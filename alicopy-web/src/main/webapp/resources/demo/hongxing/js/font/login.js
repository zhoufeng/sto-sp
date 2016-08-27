(function() {
	var o, e;
			o = [ "underscore", "angular", "common/services/domainUtils",
					"common/services/validate", "common/services/pageUtils",
					"common/services/storage", "common/services/tele",
					"common/services/generate", "front/directives/focusBind",
					"front/directives/fixAutoFill",
					"front/directives/formState", "front/directives/equals",
					"front/controllers/login/LoginPageController",
					"front/controllers/login/AccountController",
					"front/controllers/login/LoginController",
					"front/controllers/login/RegisterController",
					"common/common", "ngRoute", "ui.keypress", "ui.utils",
					"mgcrea.ngStrap" ],
			e = function(o, e, domainUtils, validate, pageUtils, storage, tele,
					generate, r, n, t, l, i, c, s, a) {
				var g;
				return
						g = e.module("login", [ "ngRoute", "ui.keypress",
								"mgcrea.ngStrap", "ui.utils", "common" ]),
						g.constant({
							LOGIN_EVENT_NAME : "virgin-login-0619",
							ERROR_LOGIN_TIME : "您的系统日期有误，无法建立安全连接，请修正系统日期后重启浏览器。",
							ERROR_LOGIN_UNKNOWN : "未知错误，可能原因是:\n1.您使用了其他代理服务器，请先关闭\n2.系统日期有误，请修改后重启浏览器\n3.红杏正在维护，请过10分钟再试"
						}), g.service({
							domainUtils : domainUtils,
							validate : validate,
							pageUtils : pageUtils,
							storage : storage,
							tele : tele,
							generate : generate
						}), g.directive({
							focusBind : r,
							fixAutoFill : n,
							formState : t,
							equals : l
						}), g.controller({
							LoginPageController : i,
							AccountController : c,
							LoginController : s,
							RegisterController : a
						}), g.config(function($routeProvider) {
							return $routeProvider.when("/", {
								templateUrl : "/partials/login/account.html",
								controller : "AccountController"
							}).when("/login", {
								templateUrl : "/partials/login/login.html",
								controller : "LoginController"
							}).when("/register", {
								templateUrl : "/partials/login/register.html",
								controller : "RegisterController"
							})
						}), e.element(document).ready(function() {
							return e.bootstrap(document, [ "login" ])
						})
			}, require([ "../config" ], function() {
				return requireWithRetry(o, e)
			})
}).call(this);