let Authorization = localStorage.getItem("Authorization");
console.log("Authorization:" + Authorization);
axios.get('/user/verify', {
    headers: {
        "Authorization": localStorage.getItem("Authorization")
    }
}).then(resp => {
    console.log(resp.data);
    if (resp.data == "401") {
        window.parent.location.href = "/login.html";
    }
});