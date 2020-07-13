<%--
  Created by IntelliJ IDEA.
  User: ThinkPad T430S
  Date: 6/10/2020
  Time: 12:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Mobile Specific Meta -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon-->
    <link rel="shortcut icon" href="img/fav.png">
    <!-- Author Meta -->
    <meta name="author" content="codepixer">
    <!-- Meta Description -->
    <meta name="description" content="">
    <!-- Meta Keyword -->
    <meta name="keywords" content="">
    <!-- meta character set -->
    <meta charset="UTF-8">
    <!-- Site Title -->
    <title>Object Detection</title>

    <!--
            Google Font
            ============================================= -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,500,600" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500i" rel="stylesheet">

    <!--
            CSS
            ============================================= -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/themify-icons/0.1.2/css/themify-icons.css">
    <link rel="stylesheet" href="css/linearicons.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/nice-select.css">
    <link rel="stylesheet" href="css/animate.min.css">
    <link rel="stylesheet" href="css/owl.carousel.css">
    <link rel="stylesheet" href="css/main.css">
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/common.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/util.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/common.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/util.js"></script>

    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/common.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/util.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/common.js"></script>
    <script type="text/javascript" charset="UTF-8" src="https://maps.googleapis.com/maps-api-v3/api/js/40/12/intl/vi_ALL/util.js"></script>
</head>
<body>
<!-- Start Header Area -->
<header id="header" class="">
    <div class="container">
        <div class="row align-items-center justify-content-between d-flex">
            <div id="logo">
                <a href="/homeSigned"><img src="https://cit.eiu.edu.vn/wp-content/uploads/2019/02/cropped-eiu-logo-1.png" alt="" title="" style="width: 152px;"></a>
            </div>
            <nav id="nav-menu-container">
                <ul class="nav-menu sf-js-enabled sf-arrows" style="touch-action: auto;">
                    <li class="menu-active"><a href="/homeSigned">Home</a></li>
<%--                    <li><a href="/dashboard">DashBoard</a></li>--%>
                    <li><a href="/">Log Out</a></li>

                </ul>
            </nav><!-- #nav-menu-container -->
        </div>
    </div>
</header>
<!-- End Header Area -->


<!-- Start Banner Area -->
<section class="home-banner-area relative">
    <div class="container">
        <div class="row fullscreen d-flex align-items-center justify-content-center" style="height: 754px;">
            <div class="banner-content col-lg-8 col-md-12">
                <h1 class="wow fadeIn" data-wow-duration="4s" style="visibility: visible; animation-duration: 4s; animation-name: fadeIn;">Object Detection <br> Input the Image/Video below</h1>
                <!--<p class="text-white">
                    In the history of modern astronomy, there is probably no one greater leap forward than the building and launch of the space
                    telescope.
                </p>-->

                <div class="input-wrap">

                    <div class="custom-file-upload">
                        <!--<label for="file">File: </label>-->
                        <input type="file" id="file" name="myfiles[]" multiple />
                    </div>


                </div>
                <h4 class="text-white">Data Recommendation</h4>

                <div class="courses pt-20">
                    <a href="#" data-wow-duration="1s" data-wow-delay=".3s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 0.3s; animation-name: fadeInDown;">People</a>
                    <a href="#" data-wow-duration="1s" data-wow-delay=".6s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 0.6s; animation-name: fadeInDown;">Trees</a>
                    <a href="#" data-wow-duration="1s" data-wow-delay=".9s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 0.9s; animation-name: fadeInDown;">Cars</a>
                    <a href="#" data-wow-duration="1s" data-wow-delay="1.2s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 1.2s; animation-name: fadeInDown;">Bicycles
                    </a>
                    <a href="#" data-wow-duration="1s" data-wow-delay="1.5s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 1.5s; animation-name: fadeInDown;">Airplans</a>
                    <a href="#" data-wow-duration="1s" data-wow-delay="1.8s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 1.8s; animation-name: fadeInDown;">Buildings
                    </a>
                    <a href="#" data-wow-duration="1s" data-wow-delay="2.1s" class="primary-btn transparent mr-10 mb-10 wow fadeInDown" style="visibility: visible; animation-duration: 1s; animation-delay: 2.1s; animation-name: fadeInDown;">Trafic Light</a>
                </div>
            </div>
        </div>
    </div>
    <div class="rocket-img">
        <img src="img/rocket.png" alt="">
    </div>
</section>
<!-- End Banner Area -->


<!-- Start About Area -->
<section class="about-area section-gap" id="ImgDted"></section>
<div id="back-top" style="display: block; opacity: 0.0282885;">
    <a title="Go to Top" href="#"></a>
</div>
<!-- ####################### End Scroll to Top Area ####################### -->

<script src="js/vendor/jquery-2.2.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="js/vendor/bootstrap.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhOdIF3Y9382fqJYt5I_sswSrEw5eihAA"></script>
<script src="js/easing.min.js"></script>
<script src="js/hoverIntent.js"></script>
<script src="js/superfish.min.js"></script>
<script src="js/jquery.ajaxchimp.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/owl-carousel-thumb.min.js"></script>
<script src="js/jquery.sticky.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/parallax.min.js"></script>
<script src="js/waypoints.min.js"></script>
<script src="js/wow.min.js"></script>
<script src="js/jquery.counterup.min.js"></script>
<script src="js/mail-script.js"></script>
<script src="js/main.js"></script>
<script src="js/1Js.js"></script>

</body>
</html>
