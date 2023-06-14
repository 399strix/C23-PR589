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

COPY . .
# Install production dependencies.
RUN pip install --no-cache-dir -r requirements.txt

# Set environment variables
ENV PYTHONUNBUFFERED True
ENV HOST 0.0.0.0
ENV PORT 8000

# Expose the specified port
EXPOSE 8000

# Run the application
CMD exec gunicorn --bind :$PORT app:app --workers 1 --threads 1 
