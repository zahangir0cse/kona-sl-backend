package com.zahangir.konasl.utils;

public final class QueryConstraint {
    private QueryConstraint(){}
    public static class ItemManagement{
        public static final String GET_LAST_30_RECORDS = "SELECT i FROM Item i ORDER BY i.id desc";
    }
}
