from PIL import Image
import tensorflow as tf
import numpy as np
# import base64
# import io

def load_model(path_artificial, path_natural):
    model_A = tf.keras.models.load_model(path_artificial)
    model_N = tf.keras.models.load_model(path_natural)

    return model_A, model_N

def convert(raw):
    image = np.array(Image.open(raw))
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

    # pil_image = Image.fromarray(np.uint8(final_image[0]))
    # image_bytes = io.BytesIO()
    # pil_image.save(image_bytes, format='JPEG')
    # image_bytes = image_bytes.getvalue()
    # image_base64 = base64.b64encode(image_bytes).decode('utf-8')

    return final_image#, image_base64

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

