# CakePHP3

[![Build Status](https://img.shields.io/travis/cakephp/app/master.svg?style=flat-square)](https://travis-ci.org/cakephp/app)
[![License](https://img.shields.io/packagist/l/cakephp/app.svg?style=flat-square)](https://packagist.org/packages/cakephp/app)

## 環境導入
1. Download [Composer](http://getcomposer.org/doc/00-intro.md)
`brew install composer`.
2. Download php71 php71-intl
`brew upgrade php`
`brew install homebrew/php/php71-intl`

3. Run `php composer.phar create-project --prefer-dist cakephp/app [app_name]`.

If Composer is installed globally, run

```bash
composer create-project --prefer-dist cakephp/app
```

In case you want to use a custom app dir name (e.g. `/myapp/`):

```bash
composer create-project --prefer-dist cakephp/app myapp
```

## 開発の仕方
##### 基本的にはコントロールにすべての処理を書いて、そこから表示に関するものをビューへ、データベースに関するものをモデルへと切り離します。
---
### ブラウザでの確認
```
bin/cake server -p 8765
```

### Postの作成
`bin/cake bake all posts`
