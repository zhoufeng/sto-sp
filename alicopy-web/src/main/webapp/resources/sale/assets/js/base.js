$(document).ready(function(){
	
	/** NAV MENU BUTTON TOGGLE **/
	$("#nav-menu-button").click(function(){
		"use strict";
		$("#nav-menu").toggleClass("show-menu");
		$("#nav-menu-button").toggleClass("show-menu");
	});
	
	
	/** BEGIN BACK TO TOP **/
	$(function () {
		$("#back-top").hide();
	});
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 100) {
				$('#back-top').fadeIn();
			} else {
				$('#back-top').fadeOut();
			}
		});
		
		$('#back-top a').click(function () {
			$('body,html').animate({
				scrollTop: 0
			}, 200);
			return false;
		});
	});
	/** END BACK TO TOP **/
	
	
	/** SQUENCE SLIDESHOW **/
    var options = {
        nextButton: false,
        prevButton: false,
        pagination: true,
        animateStartingFrameIn: true,
        autoPlay: true,
        autoPlayDelay: 5000,
        preloader: true,
		swipePreventsDefault: true,
        preloadTheseFrames: [1]
    };
    var mySequence = $("#sequence").sequence(options).data("sequence");
	
	
	/** TOOLTIP **/
	$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})
		
	/** OWL REVIEW SLIDE **/
	$("#review-slide").owlCarousel({
		navigation : false,
		slideSpeed : 300,
		paginationSpeed : 400,
		singleItem:true,
		stopOnHover : false,
		autoPlay : 5000,
		pagination: true,
		autoHeight : true,
	  });
	  

    $("#about-page").backstretch("assets/img/about.jpg");
	$("#contact-page").backstretch("assets/img/contact.jpg");

    //qq
    $('#close_im').bind('click',function(){
		$('#main-im').css("height","0");
		$('#im_main').hide();
		$('#open_im').show();
	});
	$('#open_im').bind('click',function(e){
		$('#main-im').css("height","272");
		$('#im_main').show();
		$(this).hide();
	});

	$(".weixing-container").bind('mouseenter',function(){
		$('.weixing-show').show();
	})
	$(".weixing-container").bind('mouseleave',function(){        
		$('.weixing-show').hide();
	});

});