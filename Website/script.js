document.getElementById('loan-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const data = {
        customer: {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            ssn: document.getElementById('ssn').value,
            email: document.getElementById('email').value,
            address: {
                street: document.getElementById('street').value,
                city: document.getElementById('city').value,
                postalCode: document.getElementById('postalCode').value,
                country: document.getElementById('country').value
            }
        },
        loanAmount: document.getElementById('loanAmount').value,
        term: document.getElementById('term').value
    };

    fetch('http://localhost:8080/loan', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        const message = `
            <p>${data.message}</p>
            <p><strong>UUID:</strong> ${data.uuid}</p>
            <p><strong>API Endpoint:</strong> <a href="${data.apiEndpoint}" target="_blank">${data.apiEndpoint}</a></p>
        `;
        document.getElementById('response').innerHTML = message;
        document.getElementById('response').style.display = 'block';
        document.getElementById('error').style.display = 'none';
    })
    .catch((error) => {
        document.getElementById('error').innerHTML = '<p>Error submitting loan application.</p>';
        document.getElementById('error').style.display = 'block';
        document.getElementById('response').style.display = 'none';
        console.error('Error:', error);
    });
});
