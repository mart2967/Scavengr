<!doctype html>
<html>
<head>
<meta name="layout" content="bootstrap" />
<title>Scavengr</title>

<script type="text/javascript">
	$(document).ready(function() {
		
		if('${params.signup}' == 'true'){
			$('#view-slider').show();
			$('#signupModal').modal('show');
			$('#modalLogin').focus();
		}
		if('${params.login}' == 'true'){
			showLogin();
		}
		
		$('#join').one('click', function() {
			$("#join-slider").slideDown(function() {
				$('#key').focus();
			});
		});

		$('#view').click(function() {
			document.location = 'user/myProfile';
		});
		
		$('#loginDiv').one('click', function() {
			$('#view-slider').slideDown(function() {
				
				$('#login').focus();
			});
		});
	});
	function showLogin() {
		$('#join').off('click');
		$('#view-slider').show();
		//$('#login').focus();
	}
	
	function loginTooltip() {
		$('#view-slider').slideDown(function() {
			$('#login').focus();
			$('#login').popover('show');	
		});
	}





	// for carousel 
	$(document).ready(function(){
		$('.carousel').carousel({
			interval:7000
	    });
	  });
</script>
<%-- for carousel fading--%>
<style>
      
      .carousel {
        
        overflow: hidden;
      }
      .carousel .item {
        -webkit-transition: opacity 1s;
        -moz-transition: opacity 1s;
        -ms-transition: opacity 1s;
        -o-transition: opacity 1s;
        transition: opacity 1s;
      }
      .carousel .active.left, .carousel .active.right {
        left:0;
        opacity:0;
        z-index:2;
      }
      .carousel .next, .carousel .prev {
        left:0;
        opacity:1;
        z-index:1;
      }
    </style>
</head>

<body>
<div class="container">
<div class="jumbotron masthead" style="position:absolute; z-index:2;">
	<div class="container">
		<h1 style="opacity: .8;">Scavengr</h1>
		<p>Fun, goal-oriented photo hunts for educators, explorers, and aspiring photographers.</p>
	</div>
</div>


<div id="myCarousel" class="carousel container slide">
			
					<!-- Carousel items -->
					<div class="carousel-inner">
						<div class="active item">
							<img
								style="border-radius:6px;"
								src="http://webdesigntutsplus.s3.amazonaws.com/tuts/342_bootstrap_carousel/Carousel-Files-COMPLETE/img/antennae.jpg"
								alt="">
<%--							<div style="border-radius:0px 0px 6px 6px;" class="carousel-caption">--%>
<%--								<p>The Antennae Galaxies</p>--%>
<%--							</div>--%>
						</div>
						
						<div class="item">
							<img
								style="border-radius:6px;"
								src="http://webdesigntutsplus.s3.amazonaws.com/tuts/342_bootstrap_carousel/Carousel-Files-COMPLETE/img/carina.jpg"
								alt="">
<%--							<div style="border-radius:0px 0px 6px 6px;" class="carousel-caption">--%>
<%--								<p>Carina Nebula: The Caterpillar</p>--%>
<%--							</div>--%>
						</div>
						<div class="item">
							<img
								style="border-radius:6px;"
								src="http://webdesigntutsplus.s3.amazonaws.com/tuts/342_bootstrap_carousel/Carousel-Files-COMPLETE/img/echo.jpg"
								alt="">
<%--							<div style="border-radius:0px 0px 6px 6px;" class="carousel-caption">--%>
<%--								<p>Light Echo From Star V838 Monocerotis</p>--%>
<%--							</div>--%>
						</div>
					</div>
					<!-- Carousel nav -->
<%--					<a class="carousel-control left" href="#myCarousel"--%>
<%--						data-slide="prev">&lsaquo;</a> <a class="carousel-control right"--%>
<%--						href="#myCarousel" data-slide="next">&rsaquo;</a>--%>
				</div>

	<div class="row-fluid">

		<section id="main" class="span12">

			<div class="row-fluid">

				<div class="span4 frontpage">
					<auth:ifLoggedIn>
						<div onclick="document.location = 'hunt/create';" class="btn btn-inverse frontpage-btn" id="create">
						<i class="icon-search create-icon-search" >
							<i class="icon-star create-icon-star"></i>
						</i>
						<h2>Create A Hunt</h2>
						</div>
					</auth:ifLoggedIn>
					<auth:ifNotLoggedIn>
						<div onclick="loginTooltip();" class="btn btn-inverse frontpage-btn" id="create">
						<i class="icon-search create-icon-search" >
							<i class="icon-star create-icon-star"></i>
						</i>
						<h2>Create A Hunt</h2>
						</div>
					</auth:ifNotLoggedIn>


				</div>

				<div class="span4 frontpage">
					<div class="btn btn-inverse frontpage-btn" id="join" >
						<i class="icon-camera join-icon-camera">
							<i class="icon-circle join-icon-circle"></i>
							<i class="icon-globe join-icon-globe"></i>
						</i>
						<h2>Join A Hunt</h2>
						<div id="join-slider" class="slider" hidden="true">
							<g:form class="frontpage-form" name="join" url="[controller:'hunt']" method="post" >
							<div class="control-group">
								<g:textField name="key" placeholder="Enter Key" />
							</div>
							<div class="control-group">
								<g:link controller="hunt" action="list" class="btn btn-secondary">View Public Hunts</g:link>
								<g:actionSubmit value="Go!" class="btn btn-success" action="show" />
							</div>
						</g:form>
						</div>
					</div>
				</div>






				<div class="span4 frontpage">
					<auth:ifLoggedIn>
						<div onclick="document.location='user/myProfile'" class="btn btn-inverse frontpage-btn">
							<i class="icon-user login-icon"></i>
							<h2>My Profile</h2>
						</div>
					</auth:ifLoggedIn>
					<auth:ifNotLoggedIn>
						<div class="btn btn-inverse frontpage-btn" id="loginDiv">
							<i class="icon-group login-icon"></i>
							<h2>Log In</h2>
							
							
							<div id="view-slider" class="slider" hidden="true">
							
								

								<auth:form class="frontpage-form" authAction="login" success="[controller:'user', action:'myProfile']" error="[controller:'index', action:'index']">
									<div class="control-group">
										<g:if test="${flash.authenticationFailure}">
											<bootstrap:alert class="alert-error">
												Username or password incorrect.
											</bootstrap:alert>
										</g:if>
										<g:if test="${flash.loginFormErrors}">
											<bootstrap:alert class="alert-error">Please enter both username and password.</bootstrap:alert>
										</g:if>
										<g:textField data-content="You need an account to create a hunt. Please log in or sign up to begin your adventure!" data-original-title="Welcome to Scavengr!" data-placement="bottom" name="login" placeholder="Username" value="${flash.loginForm?.login?.encodeAsHTML()}"/>
										<input type="password" name="password" placeholder="Password" />
									</div>
									
									<div class="control-group">
										<a href="#signupModal" role="button" class="btn" data-toggle="modal">Create Account</a>
										<g:actionSubmit value="Login" class="btn btn-success" />
									</div>
								</auth:form>
							</div>
						</div>

						

					</auth:ifNotLoggedIn>

				</div>
				
			</div>

		</section>
	</div>
	
</div>
</body>
</html>
