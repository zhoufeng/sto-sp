(function(){var n,e;n=[],e=function(){var n;return n=function(){return{restrict:"A",require:"?ngModel",link:function(n,e,r,t){var validate;if(t)return n.$watch(r.ngModel,function(){return validate()}),r.$observe("equals",function(){return validate()}),validate=function(){var n,e;return n=t.$viewValue,e=r.equals,t.$setValidity("equals",n===e)}}}}},define(n,e)}).call(this);