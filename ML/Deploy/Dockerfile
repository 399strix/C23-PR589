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
COPY ML/Deploy/

# Set environment variables
ENV PYTHONUNBUFFERED True
ENV HOST 0.0.0.0
ENV PORT 8000

# Expose the specified port
EXPOSE 8000

# Run the application
CMD exec gunicorn --bind :$PORT --workers 2 --threads 8 --timeout 0 app:app
