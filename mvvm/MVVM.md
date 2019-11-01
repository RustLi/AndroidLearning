## 说明

> DataBinding，ViewModel的使用

### DataBinding

用于View和Model的数据绑定。

https://developer.android.google.cn/topic/libraries/data-binding

1. gradle开启

```java
dataBinding {
    enabled = true
}
```

2. xml设置

activity_mvvm.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
        <variable
            name="viewModel"
            type="com.rustli.mvvm.DataViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/tv_test"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="176dp"
            android:text="@{viewModel.id}"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.501"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <Button
            android:id="@+id/btn_test"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="64dp"
            android:onClick="@{viewModel.loadData}"
            android:text="获取数据"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tv_test" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### ViewModel

用于View和Model沟通桥梁。

1. 初始化

```java
ActivityMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
//Method 1
final  DataViewModel viewModel = new DataViewModel();
binding.setViewModel(viewModel);

//Method 2
DataViewModel viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
binding.setViewModel(viewModel);
```

2. 控制Model

```java
//可观察的数据
public final ObservableField<String> id = new ObservableField<>();

public void loadData(View view){
    DataModel dataModel = new DataModel(model -> id.set(model.getId()));
    String testStr = "test";
    dataModel.loadModel(testStr);
}
```