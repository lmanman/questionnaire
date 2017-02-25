
angular.module('app.login.controllers', [])
    .controller('UserLoginCtrl', ['$scope','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$http,$state,$log){

    $scope.init = function(){
        $scope.uid = $stateParams.uid;
    }

    $scope.user = {};
    $scope.authError = null;
    $scope.login = function() {
        $scope.authError = null;
        // Try to login
        $http.post($scope.app.projectName+'/mobilelogin', {username: $scope.user.username, password: $scope.user.password})
            .then(function(response) {
                //$log.info(response.data);
                if ( response.data.code!='10000' ) {
                    $scope.authError = 'Account or Password not right';
                }else{
                    $scope.app.aliasName=response.data.aliasName;
                    $state.go('app.dashboard');
                }
            }, function(x) {
                $scope.authError = 'Server Error';
            });
    };

    $scope.logout = function() {
        $http.get($scope.app.projectName+'/weblogout').success(function (result) {
            $state.go('access.signin');
        }).error(function () {
            $state.go('access.signin');
        });
    }

}
