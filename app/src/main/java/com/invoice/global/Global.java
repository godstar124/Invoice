package com.invoice.global;


import java.util.ArrayList;
import java.util.List;

public class Global {

    public static final ArrayList<Integer> orderList               = new ArrayList<Integer>();
    public static final ArrayList<Integer> qtyList                 = new ArrayList<Integer>();
    public static int customerPosition                             = -1;
    public static String orderPrefix                               = "";
    public static int startOrderNum                                = 0;
    public static int tax                                          = 0;
    public static final ArrayList<String> repList                 = new ArrayList<String>();

    public static final String TAB_ORDERS                          = "ORDERS";
    public static final String TAB_CUSTOMER                        = "CUSTOMER";
    public static final String TAB_PRODUCT                         = "PRODUCT";
    public static final String TAB_SETTINGS                        = "SETTINGS";

    public static final String ORDER_DB                            = "order";
    public static final String CUSTOMER_DB                         = "customer";
    public static final String PRODUCT_DB                          = "product";
    public static final String SETTING_DB                          = "setting";

}
