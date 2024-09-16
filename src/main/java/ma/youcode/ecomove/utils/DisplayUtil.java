package ma.youcode.ecomove.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

public class DisplayUtil {

    private static final Map<Class<?>, StringFormatter> CUSTOM_FORMATTERS = new HashMap<>();

    public static void displayTable(String[] headers, List<?> data) {
        try {
            int[] columnWidths = calculateColumnWidths(headers, data);
            printFormattedHeader(headers, columnWidths);

            for (Object item : data) {
                String[] rowData = extractData(item, headers);
                printFormattedRow(rowData, columnWidths);
            }
            printSeparatorLine(columnWidths);

            System.out.println("\n(" + data.size() + " row(s))\n");

        } catch (Exception e) {
            System.err.println("Error displaying data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int[] calculateColumnWidths(String[] headers, List<?> data) {
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (Object item : data) {
            String[] rowData = extractData(item, headers);
            for (int i = 0; i < rowData.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], rowData[i].length());
            }
        }

        return columnWidths;
    }

    private static String[] extractData(Object item, String[] headers) {
        String[] rowData = new String[headers.length];
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            Object value = getFieldValue(item, header);
            rowData[i] = formatValue(value);
        }

        return rowData;
    }

    public static Object getPropertyOrField(Object item, String propertyName) {
        if (propertyName.contains(".")) {
            String[] parts = propertyName.split("\\.");
            Object currentObject = item;
            for (String part : parts) {
                currentObject = getPropertyOrField(currentObject, part);
                if (currentObject == null) {
                    return null;
                }
            }
            return currentObject;
        }

        Class<?> clazz = item.getClass();
        try {
            try {
                Field field = clazz.getDeclaredField(propertyName);
                field.setAccessible(true);
                return field.get(item);
            } catch (NoSuchFieldException e) {
                String getterName = getGetterName(propertyName);
                try {
                    Method method = clazz.getMethod(getterName);
                    return method.invoke(item);
                } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException ex) {
                    throw new RuntimeException("Error accessing property or field: " + propertyName, ex);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error accessing property or field: " + propertyName, e);
        }
    }

    public static Object getFieldValue(Object item, String fieldName) {
        try {
            return getPropertyOrField(item, fieldName);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error accessing field or property: " + fieldName, e);
        }
    }

    private static String getGetterName(String propertyName) {
        StringBuilder camelCaseName = new StringBuilder();
        camelCaseName.append(Character.toUpperCase(propertyName.charAt(0)))
                     .append(propertyName.substring(1));

        return "get" + camelCaseName.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "";
        }

        if (CUSTOM_FORMATTERS.containsKey(value.getClass())) {
            return CUSTOM_FORMATTERS.get(value.getClass()).format(value);
        }

        if (value instanceof Timestamp) {
            return ((Timestamp) value).toString();
        } else if (value instanceof Enum) {
            return ((Enum<?>) value).toString();
        } else if (value instanceof List) {
            return formatList((List<?>) value);
        } else if (value instanceof Boolean) {
            return formatBoolean((Boolean) value);
        } else {
            return value.toString();
        }
    }
    private static void displayNestedData(List<?> nestedList) {
        for (Object nestedItem : nestedList) {
            Field[] fields = nestedItem.getClass().getDeclaredFields();
            StringBuilder nestedDisplay = new StringBuilder("  - ");
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(nestedItem);
                    nestedDisplay.append(field.getName()).append(": ").append(formatValue(fieldValue)).append(", ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(nestedDisplay.toString().replaceAll(", $", ""));
        }
    }

    private static String formatList(List<?> list) {
        StringBuilder formattedList = new StringBuilder();
        for (Object item : list) {
            formattedList.append(item.toString()).append(", ");
        }
        return formattedList.toString().trim().replaceAll(", $", "");
    }

    private static String formatBoolean(Boolean value) {
        return value ? "Yes" : "No"; // Use a configurable boolean format
    }

    private static void printFormattedHeader(String[] headers, int[] columnWidths) {
        printSeparatorLine(columnWidths);
        String format = buildFormatString(columnWidths);
        System.out.printf(format, (Object[]) headers);
        printSeparatorLine(columnWidths);
    }

    private static void printFormattedRow(String[] data, int[] columnWidths) {
        String format = buildFormatString(columnWidths);
        System.out.printf(format, (Object[]) data);
    }

    private static String buildFormatString(int[] columnWidths) {
        StringBuilder format = new StringBuilder();
        for (int width : columnWidths) {
            format.append("| %-").append(width).append("s ");
        }
        format.append("|%n");
        return format.toString();
    }

    private static void printSeparatorLine(int[] columnWidths) {
        StringBuilder separator = new StringBuilder();
        separator.append("+");
        for (int width : columnWidths) {
            separator.append(new String(new char[width + 2]).replace("\0", "-")).append("+");
        }
        System.out.println(separator);
    }

    public interface StringFormatter {


        String format(Object value);
    }


}
