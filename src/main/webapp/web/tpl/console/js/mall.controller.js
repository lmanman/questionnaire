
angular.module('app.console.mall.controllers', [])
    .controller('MallCtrl', ['$scope','$stateParams','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http,$state,$log){
    $scope.id = 0;
    $scope.mallInfo = {};
    $scope.dictionaryMap = {};

    $scope.detail = function(){
        $scope.id = $stateParams.id;
        $log.info("$scope.id="+$scope.id);

        var types = ['d_city'];
        $http.post($scope.app.projectName + '/mobile/dictionary/collect/map',types).success(function(map){
            $scope.dictionaryMap = map;
            $log.info("d_city.length="+$scope.dictionaryMap.d_city.length);

            if($scope.id!=0 && $scope.id!=''){
                $http.get($scope.app.projectName + '/mobile/market/'+$scope.id).success(function(detail){
                    $scope.mallInfo = detail;
                    $log.info("name="+$scope.mallInfo.name);
                }).error(function(){
                    alert('请求失败');
                });
            }
        }).error(function(){
            alert('权限数据更新失败！');
        });

    };


    $scope.formData = {
        pageInfo: {
            pageNumber: 1,
            pageSize: 10
        }
    };
    $scope.mallList = [];
    $scope.query  = function(){
        var formData = {};
        formData.pageInfo = $scope.formData.pageInfo;
        formData.name = $scope.name;

        $http.post($scope.app.projectName + '/mobile/market/search',formData).success(function(result){
            $scope.mallList = result;
        }).error(function(){
            alert('查询失败');
        });
    };


    $scope.save = function(){
        var formData = {};
        if($scope.id!=0) {
            formData.id = $scope.mallInfo.id;
        }
        formData.name = $scope.mallInfo.name;
        formData.provinceId = $scope.mallInfo.provinceId;
        formData.cityId = $scope.mallInfo.cityId;
        formData.address = $scope.mallInfo.address;
        formData.businessHours = $scope.mallInfo.businessHours;

        $http.post($scope.app.projectName + '/mobile/market/save', formData).success(function (result) {
            if(result!=null && result.code == undefined){
                result = angular.fromJson(result);
                if(result.code == undefined){
                    result = angular.fromJson(result);
                }
            }
            if (result.code == '10000') {
                alert("保存成功");
                $state.go('app.console.mallList');
            } else {
                alert(result.msg);
                $state.go('app.console.mallList');
            }
        }).error(function () {
            alert('提交失败');
        });
    };


    $scope.deleteEhb = function(id){
        if(confirm("是否确认删除！")) {
            $http.get($scope.app.projectName+'/mobile/market/delete/'+id).success(function (result) {
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
