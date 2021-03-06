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
    <link href="./css/page-home.css" rel="stylesheet">
    <link href="./css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="./img/icon/heart_pages.ico" rel="shortcut icon">
  </head>
  <body>
    <input id="context-path" value='<%=request.getContextPath()%>' hidden/>
    <!-- main container start -->
    <div class="container">
      <!-- nav bar start -->
      <div class="row clearfix">
        <div class="col-md-12 column">
          <nav class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
            <div class="navbar-header">
              <h3 class="navbar-brand" style="margin:0px;">Our album our memory...<span class="glyphicon glyphicon-heart"></span></h3>
            </div>
            <div class="collapse navbar-collapse">
              <form class="navbar-form navbar-left">
                <div class="input-group">
                  <span class="input-group-addon">Keyword</span>
                  <input type="text" class="form-control" id="key-word">
                </div>
              </form>
              <form class="navbar-form navbar-left">
                <div class="input-group">
                  <span class="input-group-addon">Date</span>
                  <input type="text" class="form-control" id="date-start" placeholder="Year-Month-Day">
                  <span class="input-group-addon">~</span>
                  <input type="text" class="form-control" id="date-end" placeholder="Year-Month-Day">
                </div>
              </form>
              <form class="navbar-form navbar-left">
                <div class="btn-group">
                  <button type="button" class="btn btn-success" id="do-filter"><span class="glyphicon glyphicon-search"></span></button>
                  <button type="button" class="btn btn-danger" id="clear-filter"><span class="glyphicon glyphicon-refresh"></span></button>
                </div>
              </form>
              <form class="navbar-form navbar-right">
                <button type="button" class="btn btn-primary" id="add-new-photo" style="margin-right:10px;">Add new photo</button>
              </form>
            </div>
          </nav>
        </div>
      </div>
      <!-- nav bar end -->
      <!-- image area start -->
      <div class="row clearfix" style="margin-top:60px;">
        <div class="col-md-12 column">
          <div id="images" class="col-md-12 column">
          </div>
        </div>
      </div>
      <!-- image area end -->
    </div>
    <!-- main container end -->
    
    <!-- full-size image display popup start -->
    <div class="modal fade" id="full-image-popup">
      <div class="modal-dialog" style="width:60%;">
        <div class="modal-content">
          <div class="modal-body">
            <!-- progress area start -->
            <div class="row clearfix" id="img-trans-prog-area">
              <div class="col-md-12 column">
                <div class="progress progress-striped active">
                  <div id="img-trans-prog-bar" class="progress-bar" >
                    <span class="sr-only">Loading...</span>
                  </div>
                </div>
              </div>
            </div>
            <!-- progress area end -->
            <!-- image information area start -->
            <div class="row clearfix" id="img-info-area">
              <!-- full-size image display area start -->
              <div class="col-md-8 column" id="image-contents">
              </div>
              <!-- full-size image display area end -->
              <!-- full-size image description area start -->
              <div class="col-md-4 column" id="text-contents">
                <h2>Story...<small id="date-contents"></small></h2>
                <p id="description-contents"></p>
                <textarea id="description-contents-edit-area" rows="8" style="width:100%;" hidden></textarea>
                <button id="description-contents-edit-button" class="btn btn-info" type="button" style="float:left; width:100px; margin:5px;">
                  <i class="glyphicon glyphicon-pencil" style="margin:5px;"></i>Edit
                </button>
                <button id="description-contents-edit-button-confirm" class="btn btn-success" type="button" style="float:left; width:100px; margin:5px;" hidden>
                  <i class="glyphicon glyphicon-ok" style="margin:5px;"></i>Ok
                </button>
                <button id="delete-image" class="btn btn-warning" type="button" style="float:right; width:100px; margin:5px;">
                  <i class="glyphicon glyphicon-trash" style="margin:5px;"></i>Delete
                </button>
                <button id="delete-image-confirm" class="btn btn-danger" type="button" style="float:right; width:100px; margin:5px;" hidden>
                  <i class="glyphicon glyphicon-remove" style="margin:5px;"></i>Remove
                </button>
              </div>
              <!-- full-size image description area end -->
            </div>
            <!-- image information area end -->
          </div><!-- /.modal-body -->
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- image upload popup start -->
    <div class="modal fade" id="image-upload-popup">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <!-- image information area start -->
            <div class="row clearfix">
              <!-- image select area start -->
              <div class="col-md-6 column">
                <input type="file" id="upload-image-input" style="display:none">
                <!-- image container div: add img node by js -->
                <div id="upload-image-file" style="width:260px; height:230px">
                </div>
              </div>
              <!-- image select area end -->
              <!-- image description area start -->
              <div class="col-md-6 column">
                <form>
                  <div class="form-group">
                    <label>Description words</label>
                    <textarea class="form-control" id="upload-image-desc" rows="8"></textarea>
                  </div>
                </form>
              </div>
              <!-- image description area end -->
            </div>
            <!-- image information area end -->
          </div><!-- /.modal-body -->
          <div class="modal-footer">
            <div class="progress progress-striped active" style="width:75%; float:left; margin:8px;">
              <div id="img-upload-prog-bar" class="progress-bar" >
                <span class="sr-only">Uploading...</span>
              </div>
            </div>
            <label id="img-upload-res" style="float:left; margin:8px; color:blue;"></label>
            <button id="img-upload-confirm-btn" class="btn btn-success" type="button" style="float:right;"><i class="glyphicon glyphicon-cloud-upload"></i></button>
          </div><!-- /.modal-footer -->
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- Javascript -->
    <script src="./js/jquery-1.12.4.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <!-- jQuery Masonryを導入する -->
    <script src="./js/masonry.pkgd.min.js"></script>
    <script src="./js/page-home.js"></script>
    <!-- DateTimePicker -->
    <script type="text/javascript" src="./js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="./js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
  </body>
</html>