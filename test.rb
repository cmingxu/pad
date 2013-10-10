# -*- encoding : utf-8 -*-
#encoding=utf-8
require "rubygems"
require "json"
require "httparty"
require "active_support"
require "awesome_print"

WOO = '{"mLougeName":"第01层","mDanyuanName":"1单元101","mBaoxiuLeibie":"客户区域","mYezhuPhone":"13910160009","mBaoxiuRiqi":"2013/05/31","mLougebianhao":"JMJY09","mYezhuName":"王丽娥","mLoucengName":"第01层","mDanyuanBianhao":"JMJY0910101","mLoucengBianhao":"第01层","mBaoxiuNeirong":"","mBaoxiuren":"System"}'
J = JSON.parse(WOO)

url = "101.20.142.1:9000/weixiudans"

class Client
  include HTTParty
  base_uri "101.20.142.1:9000"

  def self.di
    res = (self.post URI.encode("/weixiudans?#{hash_to_query(J)}"))
    ap res
    ap res.body
    ap res.parsed_response
    ap res.code
  end
  def self.hash_to_query(h)
    res = []
    h.each_pair do |k, v|
      res  << "#{k}=#{v}"
    end
    res.join("&")
  end
end


Client.di
