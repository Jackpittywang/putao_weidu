package com.putao.wd.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanghx on 2015/12/29.
 */
public class ServiceList implements Serializable {

//        private String id;
//        private String uid;
//        private String service_type;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;
//        private String id;


//            "order_id":"518",
//            "status":"1",
//            "reply":null,
//            "area_id":"310101",
//            "area":"黄浦区",
//            "province_id":"310000",
//            "province":"上海",
//            "city_id":"310100",
//            "city":"上海市",
//            "address":null,
//            "tel":"",
//            "mobile":"18566965569",
//            "consignee":"不会后悔",
//            "postcode":null,
//            "user_express_company":null,
//            "user_express_id":null,
//            "system_express_company":null,
//            "system_express_id":null,
//            "create_time":"1451378856",
//            "update_time":"1451378856",
//            "sale_product":null,
//            "warehouse_id":nul
//    "saleTotalPrice":399,
//            "saleTotalQuantity":1,
//            "depiction":"当售后申请通过后,您还需要填写快递单号"

    private List<ServiceProduct> product;
    private ServiceSaleinfo order_info;

}
