(function($){
    $.fn.fix = function(options){
        var defaults = {
            float : 'left',
			minStatue : false,
			skin : 'blue',
			durationTime : 1000	
        }
        var options = $.extend(defaults, options);		

        this.each(function(){			
			var thisBox = $(this),
				closeBtn = thisBox.find('.close_btn' ),
				show_btn = thisBox.find('.show_btn' ),
				sideContent = thisBox.find('.side_content'),
				sideList = thisBox.find('.side_list')
				;	
			var defaultTop = thisBox.offset().top;	
			
			thisBox.css(options.float, 0);			
			if(options.minStatue){
				$(".show_btn").css("float", options.float);
				sideContent.css('width', 0);
				show_btn.css('width', 25);
				
			}
			
			if(options.skin) thisBox.addClass('side_'+options.skin);
				
						
					
			$(window).bind("scroll",function(){
				var offsetTop = defaultTop + $(window).scrollTop() + "px";
	            thisBox.animate({
	                top: offsetTop
	            },
	            {
	                duration: options.durationTime,	
	                queue: false   
	            });
			});	
			
			closeBtn.bind("click",function(){
				sideContent.animate({width: '0px'},"fast");
            	show_btn.stop(true, true).delay(300).animate({ width: '25px'},"fast");
			});
			
			 show_btn.click(function() {
	            $(this).animate({width: '0px'},"fast");
	            sideContent.stop(true, true).delay(200).animate({ width: '154px'},"fast");
	        });
				
        });	

    };
})(jQuery);