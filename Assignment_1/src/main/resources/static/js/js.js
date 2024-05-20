var app = angular.module("myApp", ["ngRoute"]);

app.controller("Controller", function($scope) {
	
});

app.config(function($routeProvider) {
	$routeProvider
		.when("/home", {
			templateUrl: "/home.html",
			controller: "Controller"
		})
		.when("/product_mypham", {
			templateUrl: "/DuocMyPham.html",
			controller: "Controller"
		})
		.when("/cart", {
			templateUrl: "/Cart.html",
			controller: "Controller"
		})
		.otherwise({
			templateUrl: "/home.html",
            controller: "TourController"
		});
});
