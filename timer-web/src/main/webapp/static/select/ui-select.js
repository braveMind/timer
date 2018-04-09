/**
 * ui-select美化插件
 * 基于jQuery
 */
; + function($) {
    "use strict";
    // 默认实例化配置
    var defaults = {
        width: null, //select的宽度，默认为null将自动获取select的宽度；
        wrapClass: '', //加载在最外层.ui-select-wrap有class，在自定义样式时有用处；
        dataKey: 'ui-select', //实例化后对象存在select的data键值，方便后续通过data('ui-select')取出；
        onChange: null, //select值改变时的回调；
        onClick: null //select元素点击时的回调，diabled时不发生。
    };
    /**
     * ui-select插件
     */
    $.fn.ui_select = function(options) {
        var _this = $(this),
            _num = _this.length;

        // 当要实例的对象只有一个时，直接实例化返回对象；
        if (_num === 1) {
            return new UI_select(_this, options);
        };
        // 当要实例的对象有多个时，循环实例化，不返回对象；
        if (_num > 1) {
            _this.each(function(index, el) {
                new UI_select($(el), options);
            })
        }
        // 当元素个数为0时，不执行实例化。
    };

    /**
     * UI_select对象
     * @param {[jQuery]} el  [jQuery选择后的对象，此处传入的为单个元素]
     * @param {[object]} opt [设置的参数]
     */
    function UI_select(el, opt) {
        this.el = el;
        this.items = this.el.find('option');
        this._opt = $.extend({}, defaults, opt);
        this._doc = $(document);
        this._win = $(window);
        return this._init();
    }

    // UI_select 原型链扩展。
    UI_select.prototype = {
        // 判断是否为IE (6-11);
        _isIE: !!window.ActiveXObject || "ActiveXObject" in window,

        // init初始化;
        _init: function() {
            var _data = this.el.data(this._opt.dataKey);
            // 如果已经实例化了，则直接返回
            if (_data)
                return _data;
            else
                this.el.data(this._opt.dataKey, this);
            this._setHtml(); // 组建元素
            this._bindEvent(); // 绑定事件
        },

        // 组建并获取相关的dom元素;
        _setHtml: function() {
            this.el.addClass('ui-select');
            var _isHide = (this.el.css('display') == 'none');
            if (_isHide) {
                this.el.show().css('visibility', 'hidden');
            }
            var _w = this._opt.width ? this._opt.width - 17 : this.el.outerWidth() - 17;
            this.el.wrap('<div tabindex="0" class="ui-select-wrap ' + this._opt.wrapClass + '"></div>')
                .after('<div class="ui-select-input"></div><i class="ui-select-arrow"></i><ul class="ui-select-list"></ul>');
            this._wrap = this.el.parent('.ui-select-wrap').css('width', _w);
            this._input = this.el.next('.ui-select-input');
            this._list = this._wrap.children('.ui-select-list');
            this.el.prop('disabled') ? this.disable() : null;
            if (_isHide) {
                this.el.add(this._wrap).hide();
                this.el.css('visibility', null);
            }
            var _ohtml = '';
            this.el.find('option').each(function(index, el) {
                var _this = $(el),
                    _text = _this.text(),
                    _value = _this.prop('value'),
                    _selected = _this.prop('selected') ? 'selected' : '',
                    _disabled = _this.prop('disabled') ? ' disabled' : '';
                _ohtml += '<li title="' + _text + '" data-value="' + _value + '" class="' + _selected + _disabled + '">' + _text + '</li> ';
            });
            this._list.html(_ohtml);
            this._items = this._list.children('li');
            this.val(this.el.val());

            var _txt = this._list.children('li.selected').text();
            this._input.text(_txt).attr('title', _txt);
        },

        // 绑定事件；
        _bindEvent: function() {
            var _this = this;
            // 模拟后的select点击事件；
            _this._wrap.on({
                // 点击事件
                'click': function(e) {
                    // 列表元素点击事件；
                    if (_this._disabled)
                        return;
                    if (e.target.tagName == 'LI') {
                        var _self = $(e.target),
                            _val = _self.attr('data-value');
                        if (_self.hasClass('disabled'))
                            return _this._isIE ? e.stopPropagation() : null;
                        _this.val(_val);
                        _this._triggerClick(_val, _this.items.eq(_self.index()));
                    }

                    // IE下点击select时其他select无法收起的bug
                    if (_this._isIE) {
                        e.stopPropagation();
                        _this._allWrap = _this._allWrap || $('.ui-select-wrap');
                        _this._allWrap.not(_this._wrap).removeClass('focus');
                        _this._doc.one('click', function() {
                            _this._allWrap.removeClass('focus');
                        });
                    }
                    _this._wrap.toggleClass('focus');
                    _this._setListCss();
                },
                // 失去焦点事件
                'blur': function(e) {
                    _this._wrap.removeClass('focus');
                },
                // 键盘事件
                'keydown': function(e) {
                    var code = e.keyCode;
                    if (code == 40 || code == 38) {
                        var _els = code == 40 ? _this._list.find('li.selected').nextAll('li') : _this._list.find('li.selected').prevAll('li');
                        var _val = _els.not('.disabled').eq(0).attr('data-value');
                        if (_val !== undefined) {
                            _this.val(_val);
                        }
                        return false;
                    }
                }
            });
            return _this;
        },

        // 根据select所处的位置设置下拉列表显示方向up / down
        _setListCss: function() {
            var _toWinTop = this._wrap.offset().top - this._win.scrollTop();
            var _toWinBottom = this._win.height() - _toWinTop - this._wrap.outerHeight();
            var _listH = this._list.outerHeight();
            if (_listH > _toWinBottom && _toWinTop > _toWinBottom) {
                this._wrap.addClass('up');
                if (_listH > _toWinTop)
                    this._list.css('maxHeight', _toWinTop - 1 + 'px');
                else
                    this._list.removeAttr('style');
            } else if (_listH > _toWinBottom && _toWinTop < _toWinBottom) {
                this._wrap.removeClass('up');
                this._list.css('maxHeight', _toWinBottom - 1 + 'px');
            } else {
                this._list.removeAttr('style');
                this._wrap.removeClass('up');
            }
        },

        // change 触发；value：值 ；item：选中的option；
        _triggerChange: function(value, item) {
            this.el.change();
            this.onChange(value, item);
            if (typeof this._opt.onChange == 'function')
                this._opt.onChange.call(this, value, item);
        },

        // click 触发；value：值 ；item：选中的option；
        _triggerClick: function(value, item) {
            this.onClick(value, item);
            if (typeof this._opt.onClick == 'function')
                this._opt.onClick.call(this, value, item);
        },

        // 获取或设置值；
        val: function(value) {
            var _val = this.el.val();
            if (value === undefined)
                return _val;
            this.el.val(value);
            var _selectedLi = this._list.children('li[data-value="' + value + '"]');
            _selectedLi.addClass('selected').siblings('li').removeClass('selected');
            this._input.html(_selectedLi.text()).attr('title', _selectedLi.text());
            if (_val != value) {
                return this._triggerChange(value, this.items.eq(_selectedLi.index()));
            }
        },

        // 值改变事件；
        onChange: function(value, item) {},

        // 点击事件；
        onClick: function(value, item) {},

        // 禁用select；
        disable: function() {
            this._disabled = true;
            this.el.prop('disabled', true);
            this._wrap.addClass('disabled').removeAttr('tabindex');
            return this;
        },

        // 启用select；
        enable: function() {
            this._disabled = false;
            this.el.prop('disabled', false);
            this._wrap.removeClass('disabled').attr('tabindex', '0');
            return this;
        },

        // 隐藏
        hide: function() {
            this._wrap.hide();
            return this;
        },

        // 显示
        show: function() {
            this._wrap.show();
            return this;
        },

        // 显示 或 隐藏
        toggle: function() {
            this._wrap.toggle();
            return this;
        },

        // visible
        visible: function(visible) {
            visible = !visible ? 'hidden' : 'visible';
            this._wrap.css('visibility', visible);
            return this;
        }

    };
}(jQuery);