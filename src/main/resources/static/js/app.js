var app = angular.module('app', ['ngRoute','ngResource','ui.bootstrap']);
app.config(function($routeProvider){
    $routeProvider
        .when('/',{
            templateUrl: '/views/trends.html',
            controller: 'trendController'
        })
        .when('/stock/:symbol',{
            templateUrl: '/views/detail.html',
            controller: 'detailController'
        })
        .otherwise(
            { redirectTo: '/views/trends.html'}
        );
});

