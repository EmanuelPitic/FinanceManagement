# app.py
from flask import Flask, render_template, request, redirect, url_for, flash, session, jsonify
import requests
import os
from functools import wraps

app = Flask(__name__)
app.secret_key = 'your_secret_key'  # Replace with a real secret key in production

# Service URLs
AUTH_SERVICE_URL = 'http://localhost:8081/auth'
EXPENSE_SERVICE_URL = 'http://localhost:8083/expenses'
ENCRYPTION_SERVICE_URL = 'http://localhost:8082/encryption'


# Middleware to check if user is logged in
def login_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if 'user_id' not in session:
            flash('Logați-vă.', 'danger')
            return redirect(url_for('login'))
        return f(*args, **kwargs)

    return decorated_function


# Decrypt user data helper function
def decrypt_data(encrypted_data):
    response = requests.post(
        f"{ENCRYPTION_SERVICE_URL}/decrypt",
        json={"data": encrypted_data}
    )
    if response.status_code == 200:
        return response.json()['data']
    return None


# Routes
@app.route('/')
def index():
    if 'user_id' in session:
        return redirect(url_for('dashboard'))
    return render_template('index.html')


@app.route('/signup', methods=['GET', 'POST'])
def signup():
    if request.method == 'POST':
        try:
            username = request.form['username']
            password = request.form['password']
            first_name = request.form['firstName']
            last_name = request.form['lastName']

            response = requests.post(
                f"{AUTH_SERVICE_URL}/signup",
                json={
                    "username": username,
                    "password": password,
                    "firstName": first_name,
                    "lastName": last_name
                }
            )

            data = response.json()
            if data['success']:
                flash('Cont creat cu succes, vă rugăm să vă logați.', 'success')
                return redirect(url_for('login'))
            else:
                flash(f'Error: {data["message"]}', 'danger')
        except Exception as e:
            flash(f'Error: {str(e)}', 'danger')

    return render_template('signup.html')


@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        try:
            username = request.form['username']
            password = request.form['password']

            response = requests.post(
                f"{AUTH_SERVICE_URL}/login",
                json={
                    "username": username,
                    "password": password
                }
            )

            data = response.json()
            if data['success']:
                # Store the user information
                user_data = data['data']
                session['user_id'] = user_data.get('id')

                # Decrypt first name and last name
                if 'encryptedFirstName' in user_data:
                    session['first_name'] = decrypt_data(user_data['encryptedFirstName'])
                if 'encryptedLastName' in user_data:
                    session['last_name'] = decrypt_data(user_data['encryptedLastName'])

                session['username'] = username

                flash('Te-ai conectat!', 'success')
                return redirect(url_for('dashboard'))
            else:
                flash(f'Login failed: {data["message"]}', 'danger')
        except Exception as e:
            flash(f'Error: {str(e)}', 'danger')

    return render_template('login.html')


@app.route('/logout')
def logout():
    session.clear()
    flash('Te-ai deconectat', 'info')
    return redirect(url_for('index'))


@app.route('/dashboard')
@login_required
def dashboard():
    try:
        # Get expenses for current user
        response = requests.get(f"{EXPENSE_SERVICE_URL}/user/{session['user_id']}")
        data = response.json()

        if data['success']:
            expenses = data['data']

            return render_template('dashboard.html',
                                   first_name=session.get('first_name', ''),
                                   expenses=expenses)
        else:
            flash(f'Error retrieving expenses: {data["message"]}', 'danger')
            return render_template('dashboard.html', expenses=[])
    except Exception as e:
        flash(f'Error: {str(e)}', 'danger')
        return render_template('dashboard.html', expenses=[])


@app.route('/expense/add', methods=['GET', 'POST'])
@login_required
def add_expense():
    if request.method == 'POST':
        try:
            amount = float(request.form['amount'])
            description = request.form.get('description', '')
            category = request.form['category']
            
            from datetime import datetime
            expense_date = request.form.get('expense_date', datetime.now().strftime('%Y-%m-%d'))
            print(f"Adding expense with date: {expense_date}")
            response = requests.post(
                f"{EXPENSE_SERVICE_URL}",
                json={
                    "userId": session['user_id'],
                    "amount": amount,
                    "description": description,
                    "category": category,
                    "expenseDate": expense_date
                }
            )

            data = response.json()
            if data['success']:
                flash('Ați adăugat cheltuiala cu succes!', 'success')
                return redirect(url_for('dashboard'))
            else:
                flash(f'Error: {data["message"]}', 'danger')
        except Exception as e:
            flash(f'Error: {str(e)}', 'danger')

    return render_template('add_expense.html')


@app.route('/expense/edit/<int:expense_id>', methods=['GET', 'POST'])
@login_required
def edit_expense(expense_id):
    if request.method == 'POST':
        try:
            amount = float(request.form['amount'])
            description = request.form.get('description', '')
            category = request.form['category']

            response = requests.put(
                f"{EXPENSE_SERVICE_URL}/{expense_id}",
                json={
                    "amount": amount,
                    "description": description,
                    "category": category
                }
            )

            data = response.json()
            if data['success']:
                flash('Factură actualizată cu succes.', 'success')
                return redirect(url_for('dashboard'))
            else:
                flash(f'Error: {data["message"]}', 'danger')
        except Exception as e:
            flash(f'Error: {str(e)}', 'danger')

    # Get current expense details
    try:
        response = requests.get(f"{EXPENSE_SERVICE_URL}/{expense_id}")
        data = response.json()

        if data['success']:
            expense = data['data']
            return render_template('edit_expense.html', expense=expense)
        else:
            flash(f'Eroare la citirea cheltuielii: {data["message"]}', 'danger')
            return redirect(url_for('dashboard'))
    except Exception as e:
        flash(f'Error: {str(e)}', 'danger')
        return redirect(url_for('dashboard'))


@app.route('/expense/delete/<int:expense_id>')
@login_required
def delete_expense(expense_id):
    try:
        response = requests.delete(f"{EXPENSE_SERVICE_URL}/{expense_id}")
        data = response.json()

        if data['success']:
            flash('Factură ștearsă cu succes!', 'success')
        else:
            flash(f'Error: {data["message"]}', 'danger')
    except Exception as e:
        flash(f'Error: {str(e)}', 'danger')

    return redirect(url_for('dashboard'))


if __name__ == '__main__':
    app.run(debug=True, port=5000, host='localhost')