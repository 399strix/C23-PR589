/*
  Warnings:

  - Made the column `description` on table `Product` required. This step will fail if there are existing NULL values in that column.

*/
-- AlterTable
ALTER TABLE `Product` MODIFY `description` VARCHAR(1500) NOT NULL;
