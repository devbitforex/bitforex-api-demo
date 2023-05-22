from time import sleep
from client import BitforexRestApi

bitforexapi = BitforexRestApi()
bitforexapi.connect("xxxx", "xxx", 3,"", "")

###print(bitforexapi.query_contract())
# print(bitforexapi.query_ticker_sync("coin-usdt-eth"))
# print(bitforexapi.query_depth_sync("coin-usdt-eth", 1))
# print(bitforexapi.query_trades_sync("coin-usdt-eth", 1))

##print(bitforexapi.query_all_open_orders_sync("coin-usdt-eth"))
# print(bitforexapi.query_account_sync())
# print(bitforexapi.query_single_account_sync("usdt"))

#print(bitforexapi.query_order_sync("coin-usdt-eth", "11111"))

# orderids = []    
# orderids.append("1111")
# orderids.append("222")
# print(bitforexapi.query_multi_order_info_sync("coin-usdt-eth", orderids))


# print(bitforexapi.send_order_sync("coin-usdt-eth", "1289.01", "1",  "1000"))

# orderInfos = []            
# d = {}
# d["price"] = float(1289.01)
# d["amount"] = float(1000)
# d["tradeType"] = 2
# orderInfos.append(d)
# d["price"] = float(1289.01)
# d["amount"] = float(1000)
# d["tradeType"] = 1
# orderInfos.append(d)
# print(bitforexapi.send_multi_order("coin-usdt-eth", orderInfos))

#print(bitforexapi.cancel_order_sync("coin-usdt-eth", "1111"))


# orderids = []    
# orderids.append("1111")
# orderids.append("222")
# print(bitforexapi.cancel_multi_order("coin-usdt-eth", orderids))
