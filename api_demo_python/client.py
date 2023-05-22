
from urllib.parse import urlencode
import collections
import time
import hmac
import hashlib
from enum import Enum
import json


from datetime import datetime, timedelta

from rest.rest_client import RequestStatus, RestClient

REST_HOST = "https://api.bitforex.com"

class BitforexRestApi(RestClient):
    def __init__(self):
        """"""
        super(BitforexRestApi, self).__init__()
        self.key = ""
        self.secret = ""

    
    def connect(
        self,
        key: str,
        secret: str,
        session: int,
        proxy_host: str,
        proxy_port: int,
    ):
        """
        Initialize connection to REST server.
        """
        self.key = key
        self.secret = secret.encode()

        self.connect_time = (
            int(datetime.now().strftime("%y%m%d%H%M%S"))
        )

        self.init(REST_HOST, proxy_host, proxy_port)
        self.start(session)
        self.write_log("REST API start success")

    def sign(self, request):
        """
        Generate Bitforex signature.
        """
        # Sign
        nonce = str(int(round(time.time() * 1000)))

        if not request.data:
            request.data = {}
            
        body = urlencode({'accessKey': self.key})
        request.data["nonce"] = nonce
        body += "&" + urlencode(collections.OrderedDict(sorted(request.data.items(), key=lambda t:t[0])))
        
        if 'orderIds=' in body or 'ordersData=' in body:
            body = body.replace('%2C', ',')
            body = body.replace('%5B', '[')
            body = body.replace('%5D', ']')
            body = body.replace('%7B', '{')
            body = body.replace('%22', '\'')
            body = body.replace('%3A', ':')
            body = body.replace('%7D', '}')
            body = body.replace('+', ' ')
            
        msg = request.path + "?" + body
        
        #print("msg:" + msg)
        
        signature = hmac.new(
            self.secret, msg.encode("utf8"), digestmod=hashlib.sha256
        ).hexdigest()      
        
        body += "&signData=" + signature
        #print("body:" + body)
        
        if request.method == "POST":
            request.data = body
        else:
            request.params = body
        
        headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
        
        request.headers = headers
        return request
    
      
    def write_log(self, logmsg):
        print(logmsg) 

    def query_contract(self):
        """"""
        return self.send_request_sync(
            method="GET",
            path="/api/v1/market/symbols"
        )   

    def query_ticker_sync(self, symbol):
        data = {
            "symbol":symbol,
            "state":0         
        }

        request, response = self.send_request_sync(
            "GET",
            "/api/v1/market/ticker",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query {symbol} ticker error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query {symbol} ticker failed:" + str(response))
            return {}, -1  
    

    def query_depth_sync(self, symbol, size = 10):
        data = {
            "symbol":symbol,
            "size":size,
            "state":0         
        }

        request, response = self.send_request_sync(
            "GET",
            "/api/v1/market/depth",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query {symbol} depth error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query {symbol} depth failed:" + str(response))
            return {}, -1  
    

    def query_trades_sync(self, symbol, size):
        data = {
            "symbol":symbol,
            "size":size,
            "state":0         
        }

        request, response = self.send_request_sync(
            "GET",
            "/api/v1/market/trades",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query {symbol} trades error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query {symbol} trades failed:" + str(response))
            return {}, -1  
  

    def query_account_sync(self):
        """"""
        request, response = self.send_request_sync(
            "POST",
            "/api/v1/fund/allAccount"
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query  allAccount error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query allAccount failed:" + str(response))
            return {}, -1  
    
    def query_account_sync(self):
        """"""
        request, response = self.send_request_sync(
            "POST",
            "/api/v1/fund/allAccount"
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query  allAccount error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query allAccount failed:" + str(response))
            return {}, -1  
    
    def query_single_account_sync(self, currency):
        """"""
        data = {
            "currency":currency
        }
        request, response =  self.send_request_sync(
            "POST",
            "/api/v1/fund/mainAccount",
            data=data          
        )

        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query {currency}  account error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query {currency} account failed:" + str(response))
            return {}, -1  
    

    def query_order_sync(self, symbol, orderid):

        data = {
            "symbol": symbol,
            "orderId": orderid
        }

        request, response = self.send_request_sync(
            "POST",
            "/api/v1/trade/orderInfo",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query order {orderid}   error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query {orderid}  failed:" + str(response))
            return {}, -1  


    def query_multi_order_info_sync(self, symbol, orderIds):
        
        data = {
            "symbol": symbol,
            "orderIds": ','.join(map(str, orderIds))
        }        
        
        request, response = self.send_request_sync(
            "POST",
            "/api/v1/trade/multiOrderInfo",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query orders {str(orderIds)}   error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query orders {str(orderIds)}  failed:" + str(response))
            return {}, -1  
    

    def query_all_open_orders_sync(self, symbol):
        data = {
            "symbol":symbol,
            "state":0         
        }

        request, response = self.send_request_sync(
            "POST",
            "/api/v1/trade/orderInfos",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"query {symbol} all open order error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"query {symbol} all open order failed:" + str(response))
            return {}, -1  
    

    def send_order_sync(self, symbol, price, direction, amount):
        """"""
        data = {
            "symbol": symbol,
            "price": price,
            "amount": amount,
            "tradeType": direction,
        }

        request, response = self.send_request_sync(
            "POST",
            path="/api/v1/trade/placeOrder",
            data=data
        )

        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"send_orde {symbol}  error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"send_orde {symbol} failed:" + str(response))
            return {}, -1  

    

    ################################################################
    # orderInfos = []            
    # d = {}
    # d["price"] = float(row.price)
    # d["amount"] = float(row.volume)
    # d["tradeType"] = 2
    # orderInfos.append(d)
    ###
    def send_multi_order(self, symbol, orderInfos):
        data = {
            "symbol": symbol,
            "ordersData": json.dumps(orderInfos)
        }

        request, response = self.send_request_sync(
            "POST",
            path="/api/v1/trade/placeMultiOrder",
            data=data
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"send_multi_order {symbol}  error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"send_multi_order {symbol} failed:" + str(response))
            return {}, -1  


    def cancel_order_sync(self, symbol, orderId):
        data = {
            "symbol": symbol,
            "orderId": orderId       
        }
        
        request, response =  self.send_request_sync(
            "POST",
            "/api/v1/trade/cancelOrder",
            data=data 
        )
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"cancel_order_sync {symbol}, {orderId}  error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"cancel_order_sync {symbol}, {orderId} failed:" + str(response))
            return {}, -1  

                  

    def cancel_multi_order(self, symbol, orderIds):
        data = {
            "symbol": symbol,
            "orderIds": ','.join(map(str, orderIds))
        }
        
        request, response =  self.send_request_sync(
            "POST",
            "/api/v1/trade/cancelMultiOrder",
            data=data
        )
    
        if request.status == RequestStatus.success:
            if response['success']:
                return response, 0
            else:
                self.write_log(f"cancel_multi_order {symbol}, {str(orderIds)}  error:" + response['code'] + "-" + response['message'])
                return {}, -1
        else:
            self.write_log(f"cancel_multi_order {symbol}, {str(orderIds)} failed:" + str(response))
            return {}, -1  