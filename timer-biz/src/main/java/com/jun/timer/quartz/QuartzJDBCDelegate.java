package com.jun.timer.quartz;

import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xunaiyang on 2017/9/20.
 */
public class QuartzJDBCDelegate extends StdJDBCDelegate {
    private String SELECT_TRIGGER_EXISTENCE = "/*+zebra:w*/ SELECT "
            + COL_TRIGGER_NAME + " FROM " + TABLE_PREFIX_SUBST + TABLE_TRIGGERS
            + " WHERE " + COL_SCHEDULER_NAME + " = " + SCHED_NAME_SUBST
            + " AND " + COL_TRIGGER_NAME + " = ? AND " + COL_TRIGGER_GROUP
            + " = ?";
    @Override
    public boolean triggerExists(Connection conn, TriggerKey triggerKey) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(rtp(SELECT_TRIGGER_EXISTENCE));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }
    }
}
