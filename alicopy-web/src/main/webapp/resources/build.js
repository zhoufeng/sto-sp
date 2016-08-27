({
    appDir: './',
    baseUrl: './js',
    dir: './dist',
    modules: [
        {
            name: 'test'
        }
    ],
    fileExclusionRegExp: /^(r|build)\.js$/,
    optimizeCss: 'standard',
    removeCombined: true,
    paths: {
        jquery: ['http://libs.baidu.com/jquery/2.0.3/jquery.js','ace/assets/js/jquery-2.0.3.min'],
        underscore: 'underscore/underscore-min',
        backbone: 'backbone/backbone',
        bootstrap:'ace/assets/js/bootstrap.min',
        gritter:"ace/assets/js/jquery.gritter.min",
		 ace:"ace/assets/js/ace.min",
        aceExtra:'ace/assets/js/ace-extra.min',
        aceElements:"ace/assets/js/ace-elements.min",
        typeaheadBs2:"ace/assets/js/typeahead-bs2.min"
    },
    shim: {
        underscore: {
            exports: '_'
        },
        backbone: {
            deps: [
                'underscore',
                'jquery'
            ],
            exports: 'Backbone'
        },
        bootstrap: {
            deps: ['jquery'],
            exports: 'Bootstrap'
        },
        gritter:{
        	 deps: ['jquery'],
        	 exports: 'Gritter'
        },
        ace:{
       	 deps: ['jquery'],
       	 exports: 'Ace'
        }, 
         aceExtra:{
      	 deps: ['jquery','ace'],
    	 exports: 'AceExtra'
         }, 
	     aceElements:{
	   	 deps: ['jquery','ace'],
		 exports: 'AceElements'
    	},
    	typeaheadBs2:{
    		deps: ['jquery','ace'],
    		exports: 'TypeaheadBs2'
    	}
    }
})