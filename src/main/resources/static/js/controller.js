app.controller('trendController', ['$scope','$http',function($scope,$http) {
    $scope.config = {
        symbol : ''
    };
    $scope.details = [];

    $scope.showDetails = function(){
        //alert("clicked");
        $http({
            method : 'GET',
            url : '/rest/'+$scope.config.symbol
        }).then(function(response){
            $scope.details.push({
                symbol : response.data.symbol,
                title  : response.data.title,
                decision : response.data.decision,
                bear : response.data.bearishPercent,
                bull : response.data.bullishPercent
            });
        });
    };
    $scope.headingTitle = "User List";
}]);

app.controller('detailController', function($scope) {
    $scope.headingTitle = "Roles List";
});
