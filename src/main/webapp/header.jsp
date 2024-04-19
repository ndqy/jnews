<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="data.jsp" flush="true"></jsp:include>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>JAVA Project</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="/Home/img/favicon.png" rel="icon">
  <link href="/Home/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Barlow:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700;1,800&display=swap" rel="stylesheet">	

	<!-- Vendor CSS Files -->
  <link href="/Home/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/Home/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="/Home/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
  <link href="/Home/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="/Home/vendor/aos/aos.css" rel="stylesheet">

  <!-- Template Main CSS Files -->
  <link href="/Home/css/variables.css" rel="stylesheet">
  <link href="/Home/css/main.css" rel="stylesheet">

  
</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    <div class="container-fluid container-xl d-flex align-items-center justify-content-between">

      <a href="/Home/" class="logo d-flex align-items-center">
        <!-- Uncomment the line below if you also wish to use an image logo -->
        <!-- <img src="/Home/img/logo.png" alt=""> -->
        <h1>JNEWS</h1>
      </a>

      <nav id="navbar" class="navbar">
        <ul>
         	<li><a href="/Home/" class="mx-2"><span class="bi bi-house"></span>&nbspTrang chủ</a></li>
       		<li><a href="/Home/tin-tuc/">Tin tức</a></li>
			<li><a href="/Home/hinh-anh">Hình ảnh</a></li>
			<li><a href="/Home/dich-vu">Dịch vụ</a></li>
       	 	<li><a href="/Home/gioi-thieu">Giới thiệu</a></li>
          	<li><a href="/Home/lien-he">Liên hệ</a></li>
        </ul>
      </nav><!-- .navbar -->

      <div class="position-relative">
        <a href="#" class="mx-2"><span class="bi-facebook"></span></a>
        <a href="#" class="mx-2"><span class="bi-twitter"></span></a>
        <a href="#" class="mx-2"><span class="bi-instagram"></span></a>

        <a href="#" class="mx-2 js-search-open"><span class="bi-search"></span></a>
        <i class="bi bi-list mobile-nav-toggle"></i>

        <!-- ======= Search Form ======= -->
        <%
        //Lấy đường dẫn xác định vị trí
    	String uri = request.getRequestURI();

        
        %>
        <script type="text/javascript" language="javascript">
        function search(fn, uri){
        	fn.method = "get";
        	fn.action = uri;
        	fn.submit();
        }
        
        function EnterKey(fn,uri,e){
        	var key;
        	if(window.event){
        		key = window.event.keyCode;//IE;
        		
        	}else{
        		key = e.which; //firefox
        	}
        	if(key == 13){
        		search(fn,uri);
        	}
        }
        
        </script>
        <div class="search-form-wrap js-search-form-wrap">
          <form action="" method = "" class="search-form">
            <span class="icon bi-search"></span>
            <input type="text" name="key" class="form-control" onKeypress="EnterKey(this.form,'<%=uri%>',event)">
            <button class="btn js-search-close"><span class="bi-x"></span></button>
          </form>
        </div><!-- End Search Form -->
        

      </div>

    </div>

  </header><!-- End Header -->