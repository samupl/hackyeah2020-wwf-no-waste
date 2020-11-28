# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Category(models.Model):
    id = models.BigAutoField(primary_key=True)
    name = models.CharField(unique=True, max_length=255)

    class Meta:
        managed = False
        db_table = 'category'


class FlywaySchemaHistory(models.Model):
    installed_rank = models.IntegerField(primary_key=True)
    version = models.CharField(max_length=50, blank=True, null=True)
    description = models.CharField(max_length=200)
    type = models.CharField(max_length=20)
    script = models.CharField(max_length=1000)
    checksum = models.IntegerField(blank=True, null=True)
    installed_by = models.CharField(max_length=100)
    installed_on = models.DateTimeField()
    execution_time = models.IntegerField()
    success = models.BooleanField()

    class Meta:
        managed = False
        db_table = 'flyway_schema_history'


class JoinProductTag(models.Model):
    product = models.OneToOneField('Product', models.DO_NOTHING, primary_key=True)
    tag = models.ForeignKey('Tag', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'join_product_tag'
        unique_together = (('product', 'tag'),)


class Product(models.Model):
    id = models.BigAutoField(primary_key=True)
    bar_code = models.CharField(unique=True, max_length=255)
    name = models.CharField(max_length=255)
    category = models.ForeignKey(Category, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'product'


class Tag(models.Model):
    id = models.BigAutoField(primary_key=True)
    name = models.CharField(unique=True, max_length=255)

    class Meta:
        managed = False
        db_table = 'tag'


class Review(models.Model):
    id = models.BigAutoField(primary_key=True)
    date = models.DateTimeField()
    author = models.CharField(max_length=255)
    comment = models.CharField(max_length=255)
    product = models.ForeignKey(Product, models.DO_NOTHING)
    box_reusable = models.IntegerField()
    box_recycable = models.IntegerField()
    box_from_recycling = models.IntegerField()
    product_reusable = models.IntegerField()
    product_recycable = models.IntegerField()
    product_from_recycling = models.IntegerField()
    repairable = models.IntegerField()

    def __str__(self) -> str:
        return f'Review: user={self.author}, product={self.product.id}'

    class Meta:
        managed = False
        db_table = 'review'
