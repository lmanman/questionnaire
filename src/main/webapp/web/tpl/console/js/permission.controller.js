
angular.module('app.console.permission.controllers', [])
    .controller('PermissionCtrl', ['$scope','$http',ctrlFn]);
;


function ctrlFn($scope,$http){

    $scope.roles = [{"id":"1","roleName":"暗访员","userNames":"Gavyn,WangNingy"},
        {"id":"2","roleName":"经理","userNames":"Ruby"},
        {"id":"3","roleName":"系统管理员","userNames":"Tom"},
    ];


    function submit(){

    }

}
