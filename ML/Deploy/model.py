import tensorflow as tf
import base64
import numpy as np

def load_model(path_artificial, path_natural):
    model_A = tf.keras.models.load_model(path_artificial)
    model_N = tf.keras.models.load_model(path_natural)

    return model_A, model_N

def convert(imgBase64):
    image = imgBase64.split(",")[1]
    image = base64.b64decode(image)
    image = tf.io.decode_image(image, channels=3, dtype=tf.float32)

    return image

def preprocess(image):
    datagen = tf.keras.preprocessing.image.ImageDataGenerator(
        rescale=1./255,
        fill_mode='nearest'
    )
    image = tf.image.resize(image, (250, 250))
    image_arr = image.numpy()
    final_image = datagen.apply_transform(image_arr, {'theta': 0})
    final_image = np.expand_dims(final_image, axis=0)
    return final_image

def predict(model, image, label):
    predictions = model.predict(image)
    pred_label = []
    pred_confidence = []
    for pred in predictions:
        label_indices = np.where(pred >= 0.3)[0]
        pred_label.append([label[i] for i in label_indices])
        pred_confidence.append([float(pred[i]) for i in label_indices])

    predictions = {}
    for a, b in zip(pred_label, pred_confidence):
        predictions[a[0]] = b[0]

    return predictions

