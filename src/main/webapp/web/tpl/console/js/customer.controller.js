
angular.module('app.console.customer.controllers', [])
    .controller('CustomerCtrl', ['$scope','$stateParams','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http,$state,$log){
    $scope.id = 0;
    $scope.dealerInfo = {};

    $scope.detail = function(){
        $scope.id = $stateParams.id;
        $log.info("$scope.id="+$scope.id);

        if($scope.id!=0 && $scope.id!=''){
            $http.get($scope.app.projectName + '/mobile/dealer/'+$scope.id).success(function(detail){
                $scope.dealerInfo = detail;
                $log.info("name="+$scope.dealerInfo.name);
            }).error(function(){
                alert('请求失败');
            });
        }

    };


    $scope.formData = {
        pageInfo: {
            pageNumber: 1,
            pageSize: 10
        }
    };
    $scope.dealerList = [];
    $scope.query  = function(){
        var formData = {};
        formData.pageInfo = $scope.formData.pageInfo;
        formData.name = $scope.name;

        $http.post($scope.app.projectName + '/mobile/dealer/search',formData).success(function(result){
            $scope.dealerList = result;
        }).error(function(){
            alert('查询失败');
        });
    };


    $scope.save = function(){
        var formData = {};
        if($scope.id!=0) {
            formData.id = $scope.dealerInfo.id;
        }
        formData.name = $scope.dealerInfo.name;
        formData.linkman = $scope.dealerInfo.linkman;
        formData.email = $scope.dealerInfo.email;
        formData.telephone = $scope.dealerInfo.telephone;

        $http.post($scope.app.projectName + '/mobile/dealer/save', formData).success(function (result) {
            if (result.code == '10000') {
                alert("保存成功");
                $state.go('app.console.dealerList');
            } else {
                alert(result.msg);
                $state.go('app.console.dealerList');
            }
        }).error(function () {
            alert('提交失败');
        });
    };


    $scope.deleteEhb = function(id){
        if(confirm("是否确认删除！")) {
            $http.get($scope.app.projectName+'/mobile/dealer/delete/'+id).success(function (result) {
                if(result.code=='10000'){
                    alert("删除成功");
                    $scope.query();
                }else{
                    alert(result.msg);
                }
            }).error(function () {
                alert('删除失败');
            });

        }
    };

}
