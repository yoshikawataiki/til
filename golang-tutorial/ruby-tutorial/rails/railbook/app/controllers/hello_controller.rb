class HelloController < ApplicationController
    def index
        render plain: 'Hello, Rails World!'
    end

    def view
        @msg = 'Hello, Rails World!'
    end
end
