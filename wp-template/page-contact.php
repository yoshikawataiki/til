<?php
/*
Template Name: お問い合わせ
*/
?>
<?php
 if(isset($_POST['submitted'])) {
//項目チェック
 if(trim($_POST['checking']) !== '') {
 $captchaError = true;
 } else {
 //名前の入力なし
 if(trim($_POST['contactName']) === '') {
 $nameError = '名前が入力されていません';
 $hasError = true;
 } else {
 $name = trim($_POST['contactName']);
 }
 //メールアドレスの間違い
 if(trim($_POST['email']) === '') {
 $emailError = 'メールアドレスが入力されていません';
 $hasError = true;
 } else if (!preg_match('|^[0-9a-z_./?-]+@([0-9a-z-]+.)+[0-9a-z-]+$|', trim($_POST['email']))) {
 $emailError = 'メールアドレスが正しくありません';
 $hasError = true;
 } else {
 $email = trim($_POST['email']);
 }
 //お問い合わせ内容の入力なし
 if(trim($_POST['comments']) === '') {
 $commentError = 'お問い合わせ内容が入力されていません';
 $hasError = true;
 } else {
 if(function_exists('stripslashes')) {
 $comments = stripslashes(trim($_POST['comments']));
 } else {
 $comments = trim($_POST['comments']);
 }
 }
 //エラーなしの場合、メール送信
 if(!isset($hasError)) {
 mb_language("japanese");
 mb_internal_encoding("UTF-8");
 $emailTo = get_option('admin_email');
 $subject = 'お問い合わせ';
 $body = "お名前: $name nnメールアドレス: $email nnお問い合わせ内容: $comments";
 $title = get_bloginfo('name');
 $from = mb_encode_mimeheader(mb_convert_encoding("$titleのお問い合わせ","UTF-8"));
 $headers = 'From: '.$from.' <'.$emailTo.'>';
 mb_send_mail($emailTo, $subject, $body, $headers);
 //自動返信用
 $subject = 'お問い合わせ受付のお知らせ';
 $from = mb_encode_mimeheader(mb_convert_encoding("$title","UTF-8"));
 $headers = 'From: '.$from.' <'.$emailTo.'>';
 $body = "$name 様 nnお問い合わせありがとうございます。n改めて担当者よりご連絡をさせていただきます。nnお名前: $name nnメールアドレス: $email nnお問い合わせ内容: $comments";
mb_send_mail($email, $subject, $body, $headers);
$emailSent = true;
}
 }
 } ?>
 <?php get_header(); ?>
 <main class="top bg-white">
 <section class="top bg-white">
    <div class="wrap">
      <div class="text">
        <h2>お問い合わせ</h2>
        <p>
          電話番号: 000-0000-0000<br />
          営業時間: 9:00〜17:00（月・火・木・金） 9:00〜12:00（水）<br />
          営業担当: <br />
        </p>
      </div>
 <?php if(isset($emailSent) && $emailSent == true) { ?>
 <h1 class="entry-title archive-title"><?=$name;?>様、お問い合わせありがとうございます。</h1>
メールを確認次第、返事をさせて頂きます。
<?php } else { ?>
 <?php if (have_posts()) : ?>
 <?php while (have_posts()) : the_post(); ?>

 <form action="<?php the_permalink(); ?>" id="contactForm" method="post">
 <div class="form-group">
 <label for="contactName">お名前</label>
 <input type="text" name="contactName" id="contactName" value="<?php if(isset($_POST['contactName'])) echo $_POST['contactName'];?>" class="requiredField" />
 <?php if($nameError != '') { ?>
 <span class="error"><?=$nameError;?></span>
 <?php } ?>
 </div>
 <div class="form-group">
 <label for="email">メールアドレス</label>
 <input type="text" name="email" id="email" value="<?php if(isset($_POST['email'])) echo $_POST['email'];?>" class="requiredField email" />
 <?php if($emailError != '') { ?>
 <span class="error"><?=$emailError;?></span>
 <?php } ?>
 </div>
 <div class="textarea"><label for="commentsText">お問い合わせ内容</label>
 <textarea name="comments" id="commentsText" rows="20" cols="30" class="requiredField"><?php if(isset($_POST['comments'])) { if(function_exists('stripslashes')) { echo stripslashes($_POST['comments']); } else { echo $_POST['comments']; } } ?></textarea>
 <?php if($commentError != '') { ?>
 <span class="error"><?=$commentError;?></span>
 <?php } ?>
 </div>
 </div class="buttons"><input type="hidden" name="submitted" id="submitted" value="true" /><input type="submit"></input></div>
 </form>
<?php endwhile; ?>
 <?php endif; ?>
 <?php } ?>
    </div>
  </section>
 </main>
 <?php get_footer(); ?>
