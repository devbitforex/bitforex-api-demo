package com.bitforex.example;

import com.bitforex.example.util.HttpUtil;
import com.google.gson.Gson;

import java.util.*;

public class ApiDemo {

    public static void ping() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        String result = HttpUtil.get("/api/v1/ping", params);
        System.out.println(result);
    }

    public static void time() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        String result = HttpUtil.get("/api/v1/time", params);
        System.out.println(result);
    }

    /**
     * Query current exchange trading rules and symbol information.
     */
    public static void querySymbols() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        String result = HttpUtil.get("/api/v1/market/symbols", params);
        System.out.println(result);
    }

    /**
     * Query current symbol ticker
     */
    public static void queryTicker() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        String result = HttpUtil.get("/api/v1/market/ticker", params);
        System.out.println(result);
    }

    /**
     * Query current symbol order book
     */
    public static void queryDepth() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("size", 10);
        String result = HttpUtil.get("/api/v1/market/depth", params);
        System.out.println(result);
    }

    /**
     * Query current symbol kline
     */
    public static void queryKline() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("size", 10);
        params.put("ktype", "30min");
        String result = HttpUtil.get("/api/v1/market/kline", params);
        System.out.println(result);
    }

    /**
     * Query current symbol recent trades list
     */
    public static void queryTrades() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("size", 10);
        String result = HttpUtil.get("/api/v1/market/trades", params);
        System.out.println(result);
    }

    /**
     * New Order
     */
    public static void newOrder() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("price", 16000);
        params.put("amount", 0.002);
        params.put("tradeType", 1);
        String result = HttpUtil.post("/api/v1/trade/placeOrder", params);
        System.out.println(result);
    }

    /**
     * Batch order
     */
    public static void multiOrder() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");

        Map<String, Object> orderMap1 = new HashMap<String, Object>();
        orderMap1.put("price", 16000);
        orderMap1.put("amount", 0.002);
        orderMap1.put("tradeType", 1);
        Map<String, Object> orderMap2 = new HashMap<String, Object>();
        orderMap2.put("price", 16500);
        orderMap2.put("amount", 0.002);
        orderMap2.put("tradeType", 1);

        List<Map<String, Object>> orderDataList = new ArrayList<Map<String, Object>>();
        orderDataList.add(orderMap1);
        orderDataList.add(orderMap2);

        Gson gson = new Gson();
        params.put("ordersData", gson.toJson(orderDataList));
        String result = HttpUtil.post("/api/v1/trade/placeMultiOrder", params);
        System.out.println(result);
    }

    /**
     * Cancel order
     */
    public static void cancelOrder() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("orderId", "af344f17-c132-49e7-bd5d-6fc092abc39b");
        String result = HttpUtil.post("/api/v1/trade/cancelOrder", params);
        System.out.println(result);
    }

    /**
     * Batch cancel order
     */
    public static void cancelMultiOrder() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("orderIds", "b819ab40-df11-4413-bd1d-77a8a0cf0577,37516233-d0c6-461e-b966-ca6657ab7936,37516233-d0c6-461e-b966-ca6657ab79123");
        String result = HttpUtil.post("/api/v1/trade/cancelMultiOrder", params);
        System.out.println(result);
    }

    /**
     * Cancel all order
     */
    public static void cancelAllOrder() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        String result = HttpUtil.post("/api/v1/trade/cancelAllOrder", params);
        System.out.println(result);
    }

    /**
     * Query order info
     */
    public static void queryOrder() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("orderId", "67c50aac-821a-4c7f-9738-e7619de448be");
        String result = HttpUtil.post("/api/v1/trade/orderInfo", params);
        System.out.println(result);
    }

    /**
     * Batch query order info
     */
    public static void multiOrderInfo() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-btc");
        params.put("orderIds", "67c50aac-821a-4c7f-9738-e7619de448be,27e0ac83-d922-495a-904a-19390415d480");
        String result = HttpUtil.post("/api/v1/trade/multiOrderInfo", params);
        System.out.println(result);
    }

    /**
     * Query my trades
     */
    public static void myTrades() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("symbol", "coin-usdt-babydoge");
        params.put("startTime", 1682914925000L);
        params.put("endTime", 1685007725900L);
        params.put("limit", 500);
        params.put("orderId", "a262d030-11a5-40fd-a07c-7ba84aa68752");
        String result = HttpUtil.post("/api/v1/trade/myTrades", params);
        System.out.println(result);
    }

    /**
     * Query balance
     */
    public static void mainAccount() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("currency", "usdt");
        String result = HttpUtil.post("/api/v1/fund/mainAccount", params);
        System.out.println(result);
    }

    /**
     * Query all balance
     */
    public static void allAccount() {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        String result = HttpUtil.post("/api/v1/fund/allAccount", params);
        System.out.println(result);
    }

    public static void main(String[] args) {
        ping();
        time();
        querySymbols();
        queryTicker();
        queryDepth();
        queryKline();
        queryTrades();
        newOrder();
        multiOrder();
        cancelOrder();
        cancelMultiOrder();
        cancelAllOrder();
        queryOrder();
        multiOrderInfo();
        mainAccount();
        allAccount();
        myTrades();
    }
}
