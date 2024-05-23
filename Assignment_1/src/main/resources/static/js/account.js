var app = angular.module("myApp", ["ngRoute"]);

app.controller("RegisterController", function ($scope, $rootScope, $location) {
  if (localStorage.getItem("registerData")) {
    $rootScope.register = JSON.parse(localStorage.getItem("registerData"));
  } else {
    $rootScope.register = [];
  }

  $scope.formData = {
    email: "",
    password: "",
    confirmPassword: "",
    phoneNumber: "",
  };

  $scope.addRegister = function () {
    if (
      !$scope.formData.email ||
      !$scope.formData.password ||
      !$scope.formData.confirmPassword ||
      !$scope.formData.phoneNumber
    ) {
      alert("Vui lòng nhập đủ thông tin.");
      return;
    }

    var newUser = {
      email: $scope.formData.email,
      password: $scope.formData.password,
      phoneNumber: $scope.formData.phoneNumber,
    };

    var userExists = $rootScope.register.some(function (user) {
      return user.email === newUser.email;
    });

    if (!userExists) {
      $rootScope.register.push(newUser);
      localStorage.setItem("registerData", JSON.stringify($rootScope.register));
      alert("Đăng ký thành công:", newUser);
    } else {
      alert("Người dùng đã tồn tại:", newUser.email);
    }

    $scope.formData.email = "";
    $scope.formData.password = "";
    $scope.formData.confirmPassword = "";
    $scope.formData.phoneNumber = "";
  };

  $scope.loginData = {
    email: "",
    password: "",
  };

  $scope.login = function () {
    if (localStorage.getItem("registerData")) {
      $rootScope.register = JSON.parse(localStorage.getItem("registerData"));

      var user = $rootScope.register.find(function (user) {
        return (
          user.email === $scope.loginData.email &&
          user.password === $scope.loginData.password
        );
      });

      if (user) {
        alert("Đăng nhập thành công: " + user.email);
        console.log("Đăng nhập thành công:", user.email);
        window.location.href = "/Index.html"; // Chuyển hướng đến trang Index.html
      } else {
        alert("Thông tin đăng nhập không chính xác.");
      }
    } else {
      alert("Chưa có người dùng đăng ký.");
    }
  };
  
});
