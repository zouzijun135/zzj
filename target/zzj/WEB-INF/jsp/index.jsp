<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to our site!</title>
    <!-- CSS -->
    <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
    <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Oleo+Script:400,700'>
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/page-index.css" rel="stylesheet">
  </head>
  <body>
    <div class="login-container container">
      <div class="row clearfix">
        <div class="logo col-md-6 column">
          <img src="./img/icon/logo.png" alt="">
        </div>
        <div class="login col-md-6 column">
          <form action="login" method="post">
            <h2>Welcome to our<span class="red"><strong>Page!</strong></span></h2>
            <div class="input-group">
              <span class="input-group-addon" for="name">Your Name</span>
              <input class="form-control" type="text" id="name" name="name" placeholder="enter your name...">
            </div>
            <div class="input-group">
              <span class="input-group-addon" for="password">Password</span>
              <input class="form-control" type="password" id="password" name="password" placeholder="choose a password...">
            </div>
            <button type="submit">LOGIN</button>
          </form>
        </div>
      </div>
    </div>
    <!-- Javascript -->
    <script src="./js/jquery-1.12.4.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="./js/jquery.backstretch.min.js"></script>
    <script src="./js/page-index.js"></script>
  </body>
</html>
