requirejs.config({
    baseUrl:'../js',
    paths: {
        jquery: '../lib/jquery/dist/jquery',
        bootstrap: '../lib/bootstrap/dist/js/bootstrap',
        underscore: '../lib/underscore/underscore',
        angular: '../lib/angular/angular',
        angularResource: '../lib/angular-resource/angular-resource',
        angularRoute: '../lib/angular-route/angular-route',
        angularAnimate: '../lib/angular-animate/angular-animate.min',
        angularStrap: '../lib/angular-strap/dist/angular-strap',
        angularStrapTpl: '../lib/angular-strap/dist/angular-strap.tpl',
        angularBindonce: '../lib/angular-bindonce/bindonce.min'
    },
    shim: {
        'angular' : {'exports' : 'angular'},
        'jquery' : {'exports' : 'jQuery'},
        'underscore':{'exports' : '_'},
        'moment':{'exports' : 'moment'},
        'angularResource' : {deps:['angular']},
        'angularAnimate' : {deps:['angular'],'exports':'angularAnimate'},
        'angularStrap' : {deps:['angular','angularAnimate']},
        'angularStrapTpl' : {deps:['angular','angularStrap']},
        'angularRoute':{deps:['angular'],'exports' : 'angularRoute'},
        'tmPanination' : {deps:['angular']},
        'bootstrap': {deps:['jquery']},
        'angularBindonce' : {deps:['angular']}
        
    },

    urlArgs: 'v=1.0.0.1'
});




requirejs([
    'appModule/common',
    'collectModule/app',
    'collectModule/controllers/index-controller',
    'collectModule/routes',
    'collectModule/models',
    'collectModule/controllers/grid-controller',
    'collectModule/controllers/store-controller',
    'collectModule/controllers/tabs-controller',
    'collectModule/services/search-facotry',
    'collectModule/services/save-facotry',
    'collectModule/services/collect-config',
    'collectModule/services/search-ali-items-service',
    'collectModule/services/search-taobao-items-service',
    'collectModule/services/btn-search-directive',
    'collectModule/services/save-ali-item-service',
    'collectModule/services/save-taobao-item-service',
    'appModule/main',
], function () {
    var angular=requirejs("angular");
    angular.bootstrap(document, ['collectModule']);
    //angular.bootstrap(angular.element("#collectApp"), ['collectModule']);
});