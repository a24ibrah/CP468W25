from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import accuracy_score, classification_report
import numpy as np

#  dataset of emails
e_mails = [
    # Spam examples
    "Win a $1000 Walmart gift card now! Click here!",
    "Limited time offer! Get a free vacation now!",
    "Congratulations! You've won a free iPhone!",
    "Urgent: Your bank account requires verification immediately!",
    "Final warning! Your account will be closed if you don't respond.",
    "Cheap online loans available! Apply now!",
    "Exclusive deal just for you! Get 50% off on electronics!",
    "Congratulations! You've been selected for a prize draw!",
    "Important: Your credit score report is available now!",
    "Final reminder: Your car insurance policy expires soon!",
    "Your account security is at risk! Update password now!",
    "Check out these new investment opportunities! Big returns!",
    # Non-spam examples
    "Meeting tomorrow at 10 AM, let's discuss the project.",
    "Can we reschedule our appointment to next Monday?",
    "Let's catch up over coffee this weekend.",
    "See you at the team lunch next Wednesday.",
    "Join our professional networking event this Friday.",
    "Your Amazon order has been shipped. Track it here.",
    "Hey, are you available for a quick call tomorrow?",
    "Invitation: Join our webinar on financial planning.",
    "Please review the attached document by EOD.",
    "The quarterly report is ready for your review.",
    "Tomorrow's meeting has been moved to Room 302.",
    "Your request has been processed successfully."
]

# Balanced labels (12 spam, 12 non-spam)
labels = np.array([1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

# Split the data into training and test sets
X_train, X_test, y_train, y_test = train_test_split(
    e_mails,
    labels,
    test_size=0.2,   
    random_state=42,
    stratify=labels   
)

# Create and fit the pipeline with optimized parameters
pipeline = Pipeline([
    ('vectorizer', TfidfVectorizer(
        stop_words='english',
        max_df=0.8,        # Stricter document frequency filtering
        min_df=1,          # Allow rarer terms
        ngram_range=(1, 3) # Include longer phrases
    )),
    ('classifier', MultinomialNB(
        alpha=0.1  # Reduced smoothing parameter
    ))
])

# Train the model
pipeline.fit(X_train, y_train)

# Cross-validation
cv_scores = cross_val_score(pipeline, X_train, y_train, cv=5)
print("Cross-validation accuracy:", cv_scores.mean())

# Predict on test set
y_pred = pipeline.predict(X_test)

# Print results
print("\nTest Set Metrics:")
print("Accuracy:", accuracy_score(y_test, y_pred))
print("\nClassification Report:\n", classification_report(y_test, y_pred))

# Predict on a new email:
# new_email = ["Hey, are we still meeting at 5 PM today? Urgent update needed!"] #not a spam email
new_email = ["Important: Your credit score is low. You urgently need to log in to your account to review your recent credit score using the following link: https://www.exmple.com/credit!"] # spam email

prediction = pipeline.predict(new_email)[0]
print("\nPrediction for new email:", "Spam" if prediction else "Not Spam")