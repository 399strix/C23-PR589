# Base image
FROM python:3.10-slim

# Install dependencies
RUN apt-get update && apt-get install -y \
    curl \
    build-essential \
    unzip

# Set working directory
WORKDIR /app

# Copy the application code
COPY app.py /app/app.py
COPY model.py /app/model.py
COPY recommender.py /app/recommender.py
COPY requirements.txt /app/requirements.txt

# Set environment variables
ENV PYTHONUNBUFFERED True
ENV HOST 0.0.0.0
ENV PORT 5000

# Expose the specified port
EXPOSE 5000

# Run the application
CMD exec gunicorn --bind :$PORT --workers 2 --threads 8 --timeout 0 app:app
