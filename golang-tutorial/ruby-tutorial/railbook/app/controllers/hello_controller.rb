class HelloController < ApplicationController
    def index
        render plain: 'こんにちは、世界！'
    end
end
