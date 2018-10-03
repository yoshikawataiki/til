#!/usr/bin/ruby

# About nokogiri

require 'open-uri'
require 'nokogiri'

doc = Nokogiri.HTML(open("https://yoshikawataiki.net/posts/basic-ruby/"))

# ページに含まれるリンクを出力する
doc.css('a').each do |element|
  puts element[:href]
end

# h2のテキストを出力する
doc2 = Nokogiri.HTML(open("https://yoshikawataiki.net/posts/basic-ruby/"))
doc.xpath('//h2').each do |e|
  puts e.text 
end
