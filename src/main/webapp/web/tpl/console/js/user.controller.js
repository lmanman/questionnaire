
angular.module('app.console.controllers', [])
    .controller('UserCtrl', ['$scope','$stateParams','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http,$state,$log){
    $scope.uid = 0;

    $scope.formData = {
        pageInfo: {
            pageNumber: 1,
            pageSize: 10
        }
    }
    $scope.list = [];
    $scope.totalPages = 1;
    $scope.total = 0;
    $scope.number = 0;
    $scope.size = 0;
    $scope.numberOfElements = 0;

    $scope.password="";
    $scope.password2="";
    $scope.confirm_password2="";


    $scope.userInfo = {};
    $scope.init = function(){
        $scope.uid = $stateParams.uid;
        if($scope.uid!=0 && $scope.uid!=''){
            $scope.password2="1";
            $scope.confirm_password2="1";
            $http.get($scope.app.projectName + '/mobile/user/'+$scope.uid).success(function(result){
                $log.info(result);

                $scope.userInfo = result;
            }).error(function(){
                alert('请求失败');
            });

        }else if($scope.uid==0){
            $http.get($scope.app.projectName + '/console/role/list').success(function(result){
                $log.info(result);

                $scope.userInfo.roleList = result;
            }).error(function(){
                alert('权限数据更新失败！');
            });
        }
    }

    $scope.query  = function(){
        var formData = {};
        formData.pageInfo = $scope.formData.pageInfo;
        formData.queryName = $scope.queryName;

        $http.post($scope.app.projectName + '/mobile/user/search',formData).success(function(result){

            $scope.list = result.content;
            $scope.lastPage = result.lastPage;
            $scope.firstPage = result.firstPage;
            $scope.total = result.totalElements;
            $scope.number = result.number;
            $scope.totalPages = result.totalPages;
            $scope.size = result.size;
            $scope.numberOfElements = result.numberOfElements;
        }).error(function(){
            alert('查询失败');
        });

    };

    $scope.roles = [];
    $scope.saveInfo = function(){
        var formData = {};
        formData.loginName = $scope.userInfo.loginName;
        formData.aliasName = $scope.userInfo.aliasName;
        formData.phoneNumber = $scope.userInfo.phoneNumber;
        formData.email = $scope.userInfo.email;
        formData.remark = $scope.userInfo.remark;

        var url = '';
        if($scope.uid>0) {
            formData.id = $scope.userInfo.id;
            url = $scope.app.projectName + '/mobile/user/update';
        }else if($scope.uid==0){
            url = $scope.app.projectName + '/mobile/user/register';

            formData.plainPassword = $scope.password2;
            formData.roleSet = [];
            angular.forEach($scope.roles, function(item){
                if(item != false && item !='false'){
                    formData.roleSet.push({"id":item})
                }
            });

            //$log.info(formData.roleSet);
        }
        $http.post(url, formData).success(function (result) {
            if (result.code == '10000') {
                alert("保存成功");
                if($scope.uid>0) {
                    $scope.app.aliasName = $scope.userInfo.aliasName;
                }
                $state.go('app.console.userList');
            } else {
                alert(result.msg);
                $state.go('app.console.userList');
            }
        }).error(function () {
            alert('提交失败');
        });
    };

    $scope.savePwd = function(){
        var formData = {"id":$scope.uid,"plainPassword":$scope.password};

        $http.post($scope.app.projectName + '/mobile/user/update/passwd',formData).success(function(result){
            if(result.code=='10000'){
                alert("密码修改成功");
                $state.go('app.console.userList');
            }else{
                alert(result.msg);
            }

        }).error(function(){
            alert('提交失败');
        });
    };

    $scope.delete = function(index,uid){
        if(confirm("是否确认删除！")) {
            $http.get($scope.app.projectName+'/mobile/user/disable/'+uid+'/1').success(function (result) {
                if(result.code=='10000'){
                    alert("删除成功");
                    $scope.list.splice(index, 1);
                }else{
                    alert(result.msg);
                }
            }).error(function () {
                alert('删除失败');
            });

        }
    };

}
