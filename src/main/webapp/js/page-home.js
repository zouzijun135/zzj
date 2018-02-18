var contextPath = $('#context-path').val();
var container = $('#images');
var imageIdList;
var imageIdPointer;
var startTime=0;
var endTime=0;
var imgDispCount = 20;

//initialize page
getPhotoIdList("","","");

$(document).ready(function(){
  // scroll event
  $(window).scroll(function(){
    // scroll at bottom
    if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
      // load data
      var limit = imageIdPointer + imgDispCount;
      for(var i = imageIdPointer; (i < imageIdList.length) && (i < limit); i++){
        imageIdPointer = i + 1;
        (function (i){
          var id = imageIdList[i];
          var xhr = new XMLHttpRequest();
          xhr.open("post", contextPath+"/getThumb", true);
          xhr.responseType = "blob";
          xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
          xhr.onload = function() {
            var blob = this.response;
            var img = document.createElement("img");
            img.setAttribute("data-id", id);
            img.src = window.URL.createObjectURL(blob);
            img.onload = function() {
              var box = document.createElement("div");
              box.setAttribute("class", "box");
              box.appendChild(img);
              container.append(box).masonry('appended', box);
              container.masonry({
                itemSelector : '.box',
                isAnimated: true,
              });
            };
          };
          xhr.send("id="+id);
        })(i);
      }
    }
  });
  // img clicked: Show full-size image information
  $(document).on("click", ".box img", function(){
    document.getElementById("img-trans-prog-bar").style.width = "0%";
    $('#img-trans-prog-area').show();
    $('#img-info-area').hide();
    $('#full-image-popup').modal('show');
    
    var id = $(this).attr("data-id");
    var param = {"id": id};
    $.ajax({
      url: contextPath + '/getPhotoDateDescription',
      type: 'POST',
      contentType : 'application/json;charset=utf-8',
      dataType:"json",
      data: JSON.stringify(param),
      success: function(data){
        var dataObj = eval("("+data+")");
        var uploadDate = dataObj["date"];
        var description = dataObj["description"];
        var dataLength = dataObj["dataLength"];
        var xhr = new XMLHttpRequest();
        xhr.onprogress = onprogress = function(e) {
          var percentComplete = Math.floor(e.loaded / dataLength * 100);
          document.getElementById("img-trans-prog-bar").style.width = percentComplete + "%";
        };
        xhr.open("post", contextPath+"/getRawPhoto", true);
        xhr.responseType = "blob";
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xhr.onload = function() {
          var blob = this.response;
          var img = document.createElement("img");
          img.setAttribute("data-id", id);
          img.src = window.URL.createObjectURL(blob);
          img.style.width = '100%';
          img.onload = function() {
            $('#img-trans-prog-area').hide();
            $('#img-info-area').show();
            $('#image-contents').empty();
            $('#image-contents').append(img);
            $('#date-contents').html(uploadDate);
            $('#description-contents').html(description);
            $('#description-contents-edit-area').hide();
            $('#description-contents-edit-area').val(description);
            $('#description-contents-edit-button').show();
            $('#description-contents-edit-button-confirm').hide();
            $('#delete-image').show();
            $('#delete-image-confirm').hide();
          };
        };
        xhr.send("id="+id);
      },
      error: function(res){
          alert(res.responseText);
      }
    });
  });
  //add photo clicked
  $("#add-new-photo").click(function(){
    var img = document.createElement("img");
    img.src = "./img/icon/add_img.png";
    img.style.marginLeft = '50px';
    img.style.marginTop = '40px';
    $("#upload-image-file").empty();
    $("#upload-image-file").append(img);
    document.getElementById("img-upload-prog-bar").style.width = "0%";
    $("#upload-image-desc").val("");
    $("#img-upload-confirm-btn").attr("disabled", true);
    $("#img-upload-res").html("");
    $("#image-upload-popup").modal('show');
  });
  //empty image clicked
  $("#upload-image-file").click(function(){
    $("#upload-image-input").click();
  });
  //image selected
  $("#upload-image-input").change(function(){
    var fileObj = document.getElementById("upload-image-input").files[0];
    var formData = new FormData();
    formData.append("file", fileObj);
    var xhr = new XMLHttpRequest();
    xhr.open("post", contextPath+"/uploadImage", true);
    xhr.responseType = "blob";
    xhr.upload.onprogress = function(evt) {
      if (evt.lengthComputable) {
        var percentComplete = Math.floor(evt.loaded / evt.total * 100);
        document.getElementById("img-upload-prog-bar").style.width = percentComplete + "%";
      }
    };
    xhr.onload = function() {
      var blob = this.response;
      var img = document.createElement("img");
      img.src = window.URL.createObjectURL(blob);
      img.onload = function() {
        var trueWidth = img.innerWidth;
        var trueHeight = img.innerHeight;
        var marginL = 0;
        var marginT = 0;
        if(trueWidth>=260){
          marginT = Math.floor((230-trueHeight)/2);
        } else if(trueHeight>=230) {
          marginL = Math.floor((260-trueWidth)/2);
        }
        img.style.marginLeft = marginL + 'px';
        img.style.marginTop = marginT + 'px';
        $("#upload-image-file").empty();
        $("#upload-image-file").append(img);
        $("#img-upload-confirm-btn").attr("disabled", false);
      };
    };
    //xhr.setRequestHeader("content-type",fileObj.type);
    xhr.send(formData);
  });
  //confirm upload
  $("#img-upload-confirm-btn").click(function(){
    var fileName = document.getElementById("upload-image-input").files[0].name;
    var description = $("#upload-image-desc").val();
    var param = {"fileName": fileName, "description": description};
    $.ajax({
      url: contextPath + '/confirmUploadImage',
      type: 'POST',
      contentType : 'application/json;charset=utf-8',
      dataType:"json",
      data: JSON.stringify(param),
      success: function(data){
        var dataObj = eval("("+data+")");
        var id = dataObj["id"];
        if(id!=""){
          imageIdList.push(id);
          $("#img-upload-res").html("success");
        }else{
          $("#img-upload-res").html("DB error");
        }
      },
      error: function(res){
        $("#img-upload-res").html("failed");
      }
    });
  });
  //date time picker
  $('#date-start').datetimepicker({
    language:  'zh-CN',
    format: 'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
  }).on('changeDate', function(ev){
    startTime=ev.date.valueOf();
    if ((endTime!=0)&&(startTime>endTime)){
      $('#date-start').val("");
      startTime=0;
      alert("Start date should be set before End date.");
    }
  });
    
  $('#date-end').datetimepicker({
    language:  'zh-CN',
    format: 'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
  }).on('changeDate', function(ev){
    endTime=ev.date.valueOf();
    if ((startTime!=0)&&(startTime>endTime)){
      $('#date-end').val("");
      endTime=0;
      alert("End date should be set after the Start date.");
    }
  });
  //do filter
  $("#do-filter").click(function(){
    var key_word=$("#key-word").val();
    var start_time=$("#date-start").val();
    var end_time=$("#date-end").val();
    $("#images").empty();
    getPhotoIdList(key_word, start_time, end_time);
  });
  //clear filter
  $("#clear-filter").click(function(){
    $("#key-word").val("");
    $("#date-start").val("");
    startTime=0;
    $("#date-end").val("");
    endTime=0;
    $("#images").empty();
    getPhotoIdList("", "", "");
  });
});

function getPhotoIdList(keyword, startDate, endDate) {
  var param = {"keyword":keyword, "startDate":startDate, "endDate":endDate};
  $.ajax({
    url: contextPath + '/getPhotoIdList',
    type: 'POST',
    contentType : 'application/json;charset=utf-8',
    dataType:"json",
    data: JSON.stringify(param),
    success: function(data){
      imageIdList = eval("("+data+")");
      if(imageIdList.length > 0 && imageIdList != ""){
        for(var i = 0; (i < imageIdList.length) && (i < imgDispCount); i++){
          imageIdPointer = i + 1;
          (function (i){
            var id = imageIdList[i];
            var xhr = new XMLHttpRequest();
            xhr.open("post", contextPath+"/getThumb", true);
            xhr.responseType = "blob";
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xhr.onload = function() {
              var blob = this.response;
              var img = document.createElement("img");
              img.setAttribute("data-id", id);
              img.src = window.URL.createObjectURL(blob);
              img.onload = function() {
                var box = document.createElement("div");
                box.setAttribute("class", "box");
                box.appendChild(img);
                container.append(box).masonry('appended', box);
                container.masonry({
                  itemSelector : '.box',
                  isAnimated: true,
                });
              };
            };
            xhr.send("id="+id);
          })(i);
        }
      };
    },
    error: function(res){
        alert(res.responseText);
    }
  });
  // edit description
  $("#description-contents-edit-button").click(function(){
    $('#description-contents').hide();
    $('#description-contents-edit-area').show();
    $(this).hide();
    $('#description-contents-edit-button-confirm').show();
  });
  // confirm edit
  $("#description-contents-edit-button-confirm").click(function(){
    var newtext=$('#description-contents-edit-area').val();
    var id=$("#image-contents img").attr("data-id");
    var param = {"id":id, "newtext":newtext};
    //update DB
    $.ajax({
      url: contextPath + '/updateDescription',
      type: 'POST',
      contentType : 'application/json;charset=utf-8',
      dataType:"json",
      data: JSON.stringify(param),
      success: function(data){
        var updatedId = eval("("+data+")");
        if(updatedId != ""){
          $('#description-contents').html(newtext);
          $('#description-contents').show();
          $('#description-contents-edit-area').hide();
          $('#description-contents-edit-button-confirm').hide();
          $('#description-contents-edit-button').show();
        }
      },
      error: function(res){
          alert(res.responseText);
      }
    });
  });
  // delete image
  $("#delete-image").click(function(){
    $('#delete-image').hide();
    $('#delete-image-confirm').show();
  });
  // confirm delete image
  $("#delete-image-confirm").click(function(){
    // delete image from DB
    var id=$("#image-contents img").attr("data-id");
    $.ajax({
      url: contextPath + '/deletePhoto',
      type: 'POST',
      contentType : 'application/x-www-form-urlencoded',
      dataType:"json",
      data: "id="+id,
      success: function(data){
        // del img node from photo wall
        var delId = eval("("+data+")");
        if(delId != ""){
          $(".box img[data-id=" + id + "]").first().parent().remove();
        }
        $('#full-image-popup').modal('hide');
      },
      error: function(res){
          alert(res.responseText);
      }
    });
  });
}