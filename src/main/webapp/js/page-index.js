
jQuery(document).ready(function() {

    /*
        Background slideshow
    */
    $.backstretch([
      "./img/backgrounds/1.jpg"
    , "./img/backgrounds/2.jpg"
    , "./img/backgrounds/3.jpg"
    , "./img/backgrounds/4.jpg"
    , "./img/backgrounds/5.jpg"
    , "./img/backgrounds/6.jpg"
    , "./img/backgrounds/7.jpg"
    , "./img/backgrounds/8.jpg"
    , "./img/backgrounds/9.jpg"
    ], {duration: 3000, fade: 750});

    /*
        Form validation
    */
    $('.login form').submit(function(){
        $(this).find("label[for='name']").html('Your Name');
        $(this).find("label[for='password']").html('Password');
        ////
        var name = $(this).find('input#name').val();
        var password = $(this).find('input#password').val();
        if(name == '') {
            $(this).find("label[for='name']").append("<span style='display:none' class='red'> - Please enter your name.</span>");
            $(this).find("label[for='name'] span").fadeIn('medium');
            return false;
        }
        if(password == '') {
            $(this).find("label[for='password']").append("<span style='display:none' class='red'> - Please enter a valid password.</span>");
            $(this).find("label[for='password'] span").fadeIn('medium');
            return false;
        }
    });
});


