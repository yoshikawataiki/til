### コントローラー
Railsを扱うにあたって、基点となるのはコントローラークラスです。
コントローラークラスは Model-View-Controllerのうち、Controllerを担うコンポーネントで、個々のリクエストに応じた処理を行います。ビジネスロジック(Model)を呼び出すのも、その結果を出力(View)に引き渡すのも、コントローラークラスの役割です。

### コントローラークラスの作成
構文
`rails generate controller name [options]`
name:コントローラー名 options:動作オプション

```
$ rails generate controller hello
      create  app/controllers/hello_controller.rb
      invoke  erb
      create    app/views/hello
      invoke  test_unit
      create    test/controllers/hello_controller_test.rb
      invoke  helper
      create    app/helpers/hello_helper.rb
      invoke    test_unit
      invoke  assets
      invoke    coffee
      create      app/assets/javascripts/hello.coffee
      invoke    scss
      create      app/assets/stylesheets/hello.scss
```

#### 補足
rails destroyコマンド
```
rails destroy controller hello
```

#### コントローラークラスの基本構文
hello_controller.rbを見てみると、最低限のコードはすでに出来ています。
```rb
class HelloController < ApplicationController
end
```

```rb
class HelloController < ApplicationController
    def index
        render plain: 'Hello, Rails World!'
    end
end
```

1. ApplicationContollerクラスを継承する
自動生成されたコードをそのまま使用する場合にはあまり意識する必要ないのですが、コントローラークラスはApplicationControllerクラス(ApplicationController::Base)を継承している必要があります。
ActionController::Baseクラスは、コントローラの基本的な機能を提供するクラスです。ActionController::Baseクラスがリクエスト/レスポンス処理に関わる基礎部分を担ってくれるので、開発者は原始的な処理を意識することなく、アプリ固有の記述に集中できます。
2. 具体的な処理を実装するのはアクションメソッド
アクションメソッドとは、クライアントからのリクエストに対して具体的な処理を実行するためのメソッドです。コントローラークラスには、1つ以上のアクションメソッドを含むことが出来ます。複数の関連するアクションをまとめたものが、コントローラーに近いです。
3. アクションメソッドの役割とは
一般的にアクションメソッドの役割は、リクエスト情報の処理やモデルの呼び出し、ビューに埋め込むテンプレート変数の設定など、さまざまです。ただし、ここではもっともシンプルにアクションから文字列を出力するだけのコードを記述します。
文字列を出力するには、renderメソッドでplainオプションを指定します。  
構文
`render plain: value`
value:出力する文字列

### ルーティングを理解する
ルーティングとは、リクエストURLに応じて処理の受け渡し先を決定すること、または、その仕組みのことです。Railsでは、クライアントからの要求を受け取ると、まずはルーティングを利用して呼び出すべきコントローラー/アクションを決定します。ルーティング設定は、/config/routes.rbに定義します。

```rb
Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  get 'hello/index', to: 'hello#index'
end
```

getメソッドはもっともシンプルな手段です。これで「http://localhost:3000/hello/index」というURLが要求されたら、hello#indexアクションを呼び出す、という意味になります。

ここでは、URLと対応するアクションとを同じ名前にしていますが、両者は一致していなくても構いません。
「http://localhost:3000/hoge/piyo」というURLでhello#indexアクションを呼び出せるようになります。
`get 'hoge/piyo', to: 'hello#index'`
両者が一致している場合には、toオプションを省略できます。
`get 'hello/index'`

### サンプルを実行
Model-View-Controllerのうち、Controllerに相当する部分だけですが、これだけでも最低限の動作は確認できます。まずは、この状態でhelloコントローラーの挙動を確認してみましょう。
`http://localhost:3000/hello/index`

![hello](../img/hello.png)

#### コントローラーの命名規則
Railsの基本的な思想は、CoC。
Railsを取得する最初の一歩は、関連するファイルやクラスの名前付けルールを理解することです。
ターミナルでhelloというコントローラーを作成しただけでコーディングを進めてきましたが、実は自動生成されたファイルは、すでにRailsの命名規則に従って用意されています。
自動生成されたファイルをそのまま利用している分にはあまり意識する必要がありませんが、改めてここでコントローラーの命名規則をまとめ、理解します。

コントローラー関連の命名規則
|種類|概要|名前（例）|
|:-----------|:------------|:------------|
|コントローラークラス|先頭は大文字で、接頭辞に「Controller」|HelloController|
|コントローラークラス（ファイル名）|コントローラークラスを小文字にしたもの、単語の区切りはアンダースコア|hello_controller.rb|
|ヘルパーファイル名|コントローラー名に接尾辞「_helper.rb」|hello_helper.rb|
|テストスクリプト名|コントローラー名に接尾辞「_controller_test.rb」|hello_controller_test.rb|