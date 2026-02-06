# 数据字典
AIFlowy 中，通过 `DictLoader` 将所有字典值按标准字段统一返回，做到了数据字典的标准化。
## 枚举类
枚举类字典主要放在：`tech.aiflowy.common.constant.enums` 包下

枚举类字典在编码过程中清晰明了，不会出现硬编码的情况。
前端回显可以通过预加载枚举值来实现，做到了前后端统一的标准。
### @DictDef 注解
用于定义一个数据字典，示例代码如下：
```java
/**
 * 通用数据状态
 */
@DictDef(name = "通用数据状态", code = "dataStatus", keyField = "code", labelField = "text")
public enum EnumDataStatus {

    UNAVAILABLE(0, "未启用"),
    AVAILABLE(1, "已启用"),
    ;

    private Integer code;
    private String text;

    EnumDataStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public static EnumDataStatus getByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (EnumDataStatus type : EnumDataStatus.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new RuntimeException("内容类型非法");
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```
其中 `code` 是该枚举的标识，`keyField` 是枚举值的字段名称，`labelField` 是枚举值的显示名称。
配合前端 `DictSelect` 组件可以一行代码轻松引入枚举选项。
```
<DictSelect v-model="entity.status" dict-code="dataStatus" />
```
## 数据表
在实际业务中，有的选项是需要从数据库中读取的，比如商品分类。
AIFlowy 中，将这一过程标准化了，你只需要配置相应的 loader 就可以像用枚举字典那样使用数据表数据了。

- 示例：我们要将 `tb_workflow_category` 的数据作为字典给前端使用。
在 `tech.aiflowy.ai.config.AiDictAutoConfig` 类中可以看到如下配置：

```java

@Resource
private WorkflowCategoryMapper workflowCategoryMapper;

@EventListener(ApplicationReadyEvent.class)
    public void onApplicationStartup() {

        DictManager dictManager = SpringContextUtil.getBean(DictManager.class);
        ...
        dictManager.putLoader(new DbDataLoader<>("aiWorkFlowCategory", 
        workflowCategoryMapper, 
        "id", 
        "category_name", 
        null, 
        null, 
        false));
        ...
    }
```
这里的代码并不是将数据库中的所有数据预先加载到内存。而是声明了一个 code 为`aiWorkFlowCategory`的字典加载器。

这个加载器绑定了 `workflowCategoryMapper`，并指定了 `id` 为 key，`category_name` 为 label。

前端在加载 `aiWorkFlowCategory` 字典数据时才会请求数据库。
## 字典数据的读取

在 AIFlowy 中，提供了一个用于读取数据字典的接口：/api/v1/dict/items/{code} 。用于传入字典 code 读取数据字典。