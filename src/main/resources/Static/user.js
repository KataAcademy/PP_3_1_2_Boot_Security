// Show User
fetch('http://localhost:8080/api/user')
    .then(res => res.json())
    .then(data => fillInformationAuthenticatedUser(data))

const fillInformationAuthenticatedUser = (post) => {
    $("#authenticatedUserTable tr").remove()
    const table = document.getElementById('authenticatedUserTable')
    const rolesNames = []
    post.roles.forEach(role => {
        rolesNames.push(role.name.substring(5))
    })
    document.getElementById('userTitle').textContent =
        post.username + ' with roles: ' + rolesNames.join(' ')
    const newRow = table.insertRow(-1)
    newRow.insertCell(0).innerText = post.id
    newRow.insertCell(1).innerText = post.username
    newRow.insertCell(2).innerText = post.lastName
    newRow.insertCell(3).innerText = post.age
    newRow.insertCell(4).innerText = post.email
    newRow.insertCell(5).innerText = rolesNames.join(' ')
}