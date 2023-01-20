function deleteGroup(id){
swal.fire({
        title: 'Are you sure you want to delete this Group ?',
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete Group!'
    }).then(function(result) {
        if (result.value) {
	var url =$("#baseUrl").val()+"/marine/user/group/delete?id="+id ;
    doGet(url,'kt_content');

      }
    });
}