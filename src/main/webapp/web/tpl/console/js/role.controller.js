
angular.module('app.console.role.controllers', [])
    .controller('RoleCtrl', ['$scope', '$modal', '$log','$http',ctrlFn]);
;


function ctrlFn($scope, $modal, $log,$http){
    $scope.roleEdit = "";
    var ModalInstanceCtrl = function ($scope, $modalInstance, roleEdit) {
        $scope.roleEdit = roleEdit;

        $scope.ok = function (editName) {
            $modalInstance.close(editName);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

    $scope.open = function (size,item) {
        $scope.roleEdit = item.roleName;
        var modalInstance = $modal.open({
            templateUrl: 'roleEdit.html',
            controller: ModalInstanceCtrl,
            size: size,
            resolve: {
                roleEdit: function () {
                    return $scope.roleEdit;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            item.roleName = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.list = [{"id":"1","roleName":"调查员","userNames":"Gavyn,WangNingy"},
        {"id":"2","roleName":"公司管理员","userNames":"Ruby"},
        {"id":"3","roleName":"系统管理员","userNames":"Tom"},
    ];



    function submit(){

    }

}
