/*!
 * jquery.pagination.js
 * http://mricle.com/JqueryPagination
 * GitHub: https://github.com/mricle/pagination
 * Version: 1.2.1
 * Date: 2015-4-14
 * 
 * Copyright 2015 Mricle
 * Released under the MIT license
 */

!function ($) {
    "use strict";

    var pageEvent = {
        pageClicked: 'pageClicked',
        jumpClicked: 'jumpClicked',
        pageSizeChanged: 'pageSizeChanged'
    }

    var Page = function (element, options) {
        var defaultOption = {
            pageSize: 10,
            pageBtnCount: 11,
            showFirstLastBtn: true,
            firstBtnText: null,
            lastBtnText: null,
            prevBtnText: "&laquo;",
            nextBtnText: "&raquo;",
            loadFirstPage: true,
            remote: {
                url: null,
                params: null,
                callback: null,
                pageIndexName: 'pageIndex',
                pageSizeName: 'pageSize',
                totalName: 'total'
            },
            showInfo: false,
            infoFormat: '{start} ~ {end} of {total} entires',
            showJump: false,
            jumpBtnText: 'Go',
            showPageSizes: false,
            pageSizeItems: null,
            debug: false
        }
        this.$element = $(element);
        this.$page = $('<ul class="m-pagination-page"></ul>');
        this.$size = $('<div class="m-pagination-size"></div>');
        this.$jump = $('<div class="m-pagination-jump"></div>');
        this.$info = $('<div class="m-pagination-info"></div>');
        this.options = $.extend(true, {}, defaultOption, $.fn.page.defaults, options);
        this.total = this.options.total || 0;
        this.options.pageSizeItems = this.options.pageSizeItems || [5, 10, 15, 20],
        this.currentPageIndex = 0;
        this.currentPageSize = this.options.pageSize;
        this.pageCount = this.getPageCount(this.total, this.currentPageSize);

        var init = function (obj) {
            var that = obj;

            //init size module
            var html = $('<select data-page-btn="size"></select>');
            for (var i = 0; i < that.options.pageSizeItems.length; i++) {
                html.append('<option value="' + that.options.pageSizeItems[i] + '">' + that.options.pageSizeItems[i] + '</option>')
            }
            html.val(that.currentPageSize);
            that.$size.append(html);

            //init jump module
            var jumpHtml = '<div class="m-pagination-group"><input type="text"><button data-page-btn="jump" type="button">' + that.options.jumpBtnText + '</button></div>';
            that.$jump.append(jumpHtml);
            that.$jump.find('input').change(function () {
                var $this = $(this);
                if (!checkPageIndex($this.val(), that.pageCount)) {
                    $this.val(null);
                }
            });

            that.$element.append(that.$page);
            if (that.options.showPageSizes) that.$element.append(that.$size);
            if (that.options.showJump) that.$element.append(that.$jump);
            if (that.options.showInfo) that.$element.append(that.$info);

            that._remoteOrRedner(0, that.options.loadFirstPage);

            that.$element
                .on('click', { page: that }, function (event) { eventHandler(event); })
                .on('change', { page: that }, function (event) { eventHandler(event); });
        }

        var eventHandler = function (event) {
            var that = event.data.page;
            var $target = $(event.target);
            if (event.type === 'click' && $target.data('pageIndex') !== undefined && !$target.parent().hasClass('active')) {
                var pageIndex = $(event.target).data("pageIndex");
                that.$element.trigger(pageEvent.pageClicked, pageIndex + 1);
                that.debug('event[ pageClicked ] : data = ' + (pageIndex + 1));
                that._remoteOrRedner(pageIndex);
            }
            else if (event.type === 'click' && $target.data('pageBtn') === 'jump') {
                var pageIndexStr = that.$jump.find('input').val();
                if (checkPageIndex(pageIndexStr, that.pageCount)) {
                    var pageIndex = pageIndexStr - 1;
                    that.$element.trigger(pageEvent.jumpClicked, pageIndex + 1);
                    that.debug('event[ jumpClicked ] : data = ' + (pageIndex + 1));
                    that._remoteOrRedner(pageIndex);
                }
                that.$jump.find('input').val(null);
            }
            else if (event.type === 'change' && $target.data('pageBtn') === 'size') {
                var pageSize = that.$size.find('select').val();
                that.currentPageSize = pageSize;
                that.$element.trigger(pageEvent.pageSizeChanged, pageSize);
                that.debug('event[ pageSizeChanged ] : data = ' + pageSize);
                that._remoteOrRedner(0);
            }
        }


        if (typeof this.options.total === 'undefined' && this.options.remote.url === null) {
            console && console.error("[init error] : the options must have the parameter of 'remote.url' or 'total'.");
        }
        else if (typeof this.options.total === 'undefined' && !this.options.loadFirstPage) {
            console && console.error("[init error] : if you don't remote the first page. you must set the options or 'total'.");
        }
        else {
            init(this);
        }
    }

    Page.prototype = {
        _remoteOrRedner: function (pageIndex, loadFirstPage) {
            if (this.options.remote.url) {
                if (loadFirstPage == false)
                    this.renderPagination(pageIndex);
                else
                    this.remote(pageIndex);
            } else {
                this.renderPagination(pageIndex);
            }
        },

        //remote request data and renderPagination
        remote: function (pageIndex, params) {
            var that = this;
            if (typeof pageIndex === "object") {
                params = pageIndex;
                pageIndex = that.currentPageIndex;
            }
            if (typeof pageIndex !== "number" && typeof pageIndex !== "string") {
                pageIndex = that.currentPageIndex;
            }

            if (params) {
                this.options.remote.params = params;
            }
            var pageParams = {};
            pageParams[this.options.remote.pageIndexName] = pageIndex;
            pageParams[this.options.remote.pageSizeName] = this.currentPageSize;
            var requestParams = $.extend({}, this.options.remote.params, pageParams);

            $.ajax({
                url: this.options.remote.url,
                dataType: 'json',
                data: requestParams,
                contentType: 'application/json',
                success: function (result) {

                    //that.debug("ajax resopnse : params = " + JSON.stringify(requestParams), result);
                    var total = 0;
                    var arr = that.options.remote.totalName.split('.');
                    switch (arr.length) {
                        case 1: total = result[arr[0]]; break;
                        case 2: total = result[arr[0]][arr[1]]; break;
                        case 3: total = result[arr[0]][arr[1]][arr[2]]; break;
                        case 4: total = result[arr[0]][arr[1]][arr[2]][arr[3]]; break;
                        case 5: total = result[arr[0]][arr[1]][arr[2]][arr[3]][arr[4]]; break;
                    }
                    if (total == undefined) {
                        console && console.error("the response of totalName :  '" + that.options.remote.totalName + "'  not found");
                    } else {
                        that.total = total;
                        if (typeof that.options.remote.callback === 'function') that.options.remote.callback(result);
                        that.renderPagination(pageIndex);
                    }
                }
            })
        },

        //render page and update page info
        renderPagination: function (pageIndex) {
            this.currentPageIndex = pageIndex;
            var html = this._render(pageIndex);
            this.$page.empty().append(html);
            this.$info.text(this.renderInfo());
        },

        _render: function (pageIndex) {
            pageIndex = pageIndex == undefined ? 1 : pageIndex + 1;      //set pageIndex from 1， convenient calculation page
            if (typeof this.total == 'undefined') { console && console.error('total is undefined'); }
            var pageCount = this.getPageCount(this.total, this.currentPageSize);
            this.pageCount = pageCount;
            var pageBtnCount = this.options.pageBtnCount;

            var html = [];

            if (pageCount <= pageBtnCount) {
                html = this.renderPage(1, pageCount, pageIndex);
            }
            else {
                var firstPage = this.renderPerPage(this.options.firstBtnText || 1, 0);
                var lastPage = this.renderPerPage(this.options.lastBtnText || pageCount, pageCount - 1);
                //button count of  both sides
                var symmetryBtnCount = (pageBtnCount - 1 - 2) / 2;
                if (!this.options.showFirstLastBtn)
                    symmetryBtnCount = symmetryBtnCount - 1;
                var frontBtnNum = (pageBtnCount + 1) / 2;
                var behindBtnNum = pageCount - ((pageBtnCount - 1) / 2);

                if (pageIndex <= frontBtnNum) {
                    if (this.options.showFirstLastBtn) {
                        html = this.renderPage(1, pageBtnCount - 1, pageIndex);
                        html[html.length - 1].find("a").html(this.options.nextBtnText);
                        html.push(lastPage);
                    } else {
                        html = this.renderPage(1, pageBtnCount, pageIndex);
                        html[html.length - 1].find("a").html(this.options.nextBtnText);
                    }
                }
                else if (pageIndex >= behindBtnNum) {
                    if (this.options.showFirstLastBtn) {
                        html = this.renderPage(pageCount - pageBtnCount + 2, pageBtnCount - 1, pageIndex);
                        html[0].find("a").html(this.options.prevBtnText);
                        html.unshift(firstPage);
                    } else {
                        html = this.renderPage(pageCount - pageBtnCount + 1, pageBtnCount, pageIndex);
                        html[0].find("a").html(this.options.prevBtnText);
                    }
                }
                else {
                    if (this.options.showFirstLastBtn) {
                        html = this.renderPage(pageIndex - symmetryBtnCount, pageBtnCount - 2, pageIndex);
                        html[0].find("a").html(this.options.prevBtnText);
                        html[html.length - 1].find("a").html(this.options.nextBtnText);
                        html.unshift(firstPage);
                        html.push(lastPage);
                    } else {
                        html = this.renderPage(pageIndex - symmetryBtnCount - 2, pageBtnCount, pageIndex);
                        html[0].find("a").html(this.options.prevBtnText);
                        html[html.length - 1].find("a").html(this.options.nextBtnText);
                    }
                }
            }
            return html;
        },
        renderInfo: function () {
            var startNum = (this.currentPageIndex * this.currentPageSize) + 1;
            var endNum = (this.currentPageIndex + 1) * this.currentPageSize;
            endNum = endNum >= this.total ? this.total : endNum;
            var info = this.options.infoFormat;
            return info.replace('{start}', startNum).replace('{end}', endNum).replace('{total}', this.total);
        },
        renderPerPage: function (text, value) {
            var $pageBtn = $("<li><a data-page-index='" + value + "'>" + text + "</a></li>");
            return $pageBtn;
        },
        renderPage: function (beginPageNum, count, currentPage) {
            var html = [];
            for (var i = 0; i < count; i++) {
                var page = this.renderPerPage(beginPageNum, beginPageNum - 1);
                if (beginPageNum == currentPage) { page.addClass("active"); }
                html.push(page);
                beginPageNum++;
            }
            return html;
        },
        getPageCount: function (total, pageSize) {
            var total = parseInt(total);
            var pageCountStr = (total / pageSize).toString();
            var dotIndex = pageCountStr.indexOf('.');
            return dotIndex == -1 ? parseInt(pageCountStr) : parseInt(pageCountStr.substring(0, dotIndex)) + 1;
        },
        destroy: function () {
            this.$element.data("page", null).empty();
        },
        debug: function (message, data) {
            if (this.options.debug && console) {
                message && console.info(message);
                data && console.info(data);
            }
        }
    }

    var checkPageIndex = function (pageIndex, maxPage) {
        var reg = /^\+?[1-9][0-9]*$/;
        var result = reg.test(pageIndex);
        if (result) {
            result = pageIndex <= maxPage;
        }
        return result;
    }

    $.fn.page = function (option) {
        var args = arguments;
        return this.each(function () {
            var $this = $(this);
            var data = $this.data('page');
            if (!data && (typeof option === 'object' || typeof option === 'undefined')) {
                var options = typeof option == 'object' && option;
                var data_api_options = $this.data();
                options = $.extend(options, data_api_options);
                $this.data('page', (data = new Page(this, options)));
            }
            else if (data && typeof option === 'string') {
                data[option].apply(data, Array.prototype.slice.call(args, 1));
            }
            else {
                $this.data('page', (data = new Page(this)));
            }
        });
    }
}(window.jQuery)