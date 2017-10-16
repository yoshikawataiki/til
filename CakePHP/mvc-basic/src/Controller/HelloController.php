<?php
namespace App\Controller;

/**
 * hello world! と出力
 */
class HelloController extends AppController
{
  public $name = 'hello';
  public $autoRender = false;
  public function index() {
    echo "hello world";
  }
}
