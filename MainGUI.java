



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI extends JFrame {

    private static ArrayList<AidRequest> requests = new ArrayList<>();
    private DefaultTableModel tableModel;

    // Input fields
    private JTextField txtFamilyName, txtLocation, txtPhone, txtMembers;
    private JCheckBox chkDisabled, chkChronicIll, chkUrgent;
    private JTextField txtUrgencyType;
    private JSpinner spnUrgencyLevel;
    private JCheckBox[] chkAidTypes;
    private JTable table;
    private JLabel lblStats;

    public MainGUI() {
        setTitle("Smart Humanitarian Aid Tracker — غزة");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Header ---
        JLabel header = new JLabel("Smart Humanitarian Aid Tracker 🇵🇸", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setForeground(new Color(110, 15, 56));
        header.setBorder(BorderFactory.createEmptyBorder(12, 0, 8, 0));
        add(header, BorderLayout.NORTH);

        // --- Left Panel: Input Form ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("تسجيل طلب مساعدة"));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Family fields
        leftPanel.add(new JLabel("اسم العائلة:"), gbc); gbc.gridy++;
        txtFamilyName = new JTextField(); leftPanel.add(txtFamilyName, gbc); gbc.gridy++;

        leftPanel.add(new JLabel("الموقع/المنطقة:"), gbc); gbc.gridy++;
        txtLocation = new JTextField(); leftPanel.add(txtLocation, gbc); gbc.gridy++;

        leftPanel.add(new JLabel("رقم الهاتف:"), gbc); gbc.gridy++;
        txtPhone = new JTextField(); leftPanel.add(txtPhone, gbc); gbc.gridy++;

        leftPanel.add(new JLabel("عدد الأفراد:"), gbc); gbc.gridy++;
        txtMembers = new JTextField("1"); leftPanel.add(txtMembers, gbc); gbc.gridy++;

        chkDisabled = new JCheckBox("يوجد معاق");
        chkChronicIll = new JCheckBox("يوجد مريض مزمن");
        leftPanel.add(chkDisabled, gbc); gbc.gridy++;
        leftPanel.add(chkChronicIll, gbc); gbc.gridy++;

        // Aid types checkboxes
        leftPanel.add(new JLabel("أنواع المساعدة المطلوبة:"), gbc); gbc.gridy++;
        String[] types = AidRequest.VALID_AID_TYPES;
        chkAidTypes = new JCheckBox[types.length];
        for (int i = 0; i < types.length; i++) {
            chkAidTypes[i] = new JCheckBox(types[i]);
            leftPanel.add(chkAidTypes[i], gbc); gbc.gridy++;
        }

        // Urgent
        chkUrgent = new JCheckBox("حالة طارئة");
        chkUrgent.setForeground(Color.RED);
        leftPanel.add(chkUrgent, gbc); gbc.gridy++;

        leftPanel.add(new JLabel("نوع الطوارئ:"), gbc); gbc.gridy++;
        txtUrgencyType = new JTextField("إصابة"); leftPanel.add(txtUrgencyType, gbc); gbc.gridy++;

        leftPanel.add(new JLabel("مستوى الإلحاح (1-10):"), gbc); gbc.gridy++;
        spnUrgencyLevel = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        leftPanel.add(spnUrgencyLevel, gbc); gbc.gridy++;

        // Buttons
        JButton btnAdd = new JButton("إضافة الطلب");
        btnAdd.setBackground(new Color(141, 29, 158));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy++;
        leftPanel.add(btnAdd, gbc);

        JButton btnSave = new JButton("حفظ في ملف");
        gbc.gridy++;
        leftPanel.add(btnSave, gbc);

        JButton btnLoad = new JButton("تحميل من ملف");
        gbc.gridy++;
        leftPanel.add(btnLoad, gbc);

        add(leftPanel, BorderLayout.WEST);

        // --- Center: Table ---
        String[] cols = {"#", "العائلة", "الموقع", "عدد الأفراد", "المساعدات", "الأولوية", "الحالة"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return c == 6; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        // Status dropdown in table
        JComboBox<String> statusBox = new JComboBox<>(
                new String[]{"قيد الانتظار", "موافق عليه", "تم التسليم", "عاجل - قيد المراجعة"});
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusBox));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("قائمة طلبات المساعدة"));
        add(scroll, BorderLayout.CENTER);

        // --- Bottom: Stats ---
        lblStats = new JLabel("إجمالي الطلبات: 0 | قيد الانتظار: 0 | موافق: 0 | مسلّم: 0");
        lblStats.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        add(lblStats, BorderLayout.SOUTH);

        // --- Listeners ---
        btnAdd.addActionListener(e -> addRequest());
        btnSave.addActionListener(e -> {
            FileManager.saveRequests(requests);
            JOptionPane.showMessageDialog(this, "تم الحفظ بنجاح!");
        });
        btnLoad.addActionListener(e -> {
            FileManager.loadAndPrintRequests();
            JOptionPane.showMessageDialog(this, "تم التحميل — راجع الـ console");
        });

        setVisible(true);
    }

    private void addRequest() {
        try {
            // Validation with Exceptions
            String name = txtFamilyName.getText().trim();
            if (name.isEmpty()) throw new InvalidRequestException("اسم العائلة", "الاسم لا يمكن أن يكون فارغاً");

            String location = txtLocation.getText().trim();
            if (location.isEmpty()) throw new InvalidRequestException("الموقع", "الموقع مطلوب");

            int members;
            try {
                members = Integer.parseInt(txtMembers.getText().trim());
                if (members <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                throw new InvalidRequestException("عدد الأفراد", "يجب أن يكون رقماً صحيحاً موجباً");
            }

            // Collect selected aid types
            ArrayList<String> selectedTypes = new ArrayList<>();
            for (JCheckBox cb : chkAidTypes) {
                if (cb.isSelected()) selectedTypes.add(cb.getText());
            }
            if (selectedTypes.isEmpty()) throw new InvalidRequestException("المساعدات", "اختر نوعاً واحداً على الأقل");

            String[] aidArr = selectedTypes.toArray(new String[0]);
            int[] qtyArr = new int[aidArr.length];
            for (int i = 0; i < qtyArr.length; i++) qtyArr[i] = members; // simple qty

            Family family = new Family(name, location, txtPhone.getText(),
                    members, chkDisabled.isSelected(), chkChronicIll.isSelected());

            AidRequest req;
            if (chkUrgent.isSelected()) {
                req = new UrgentRequest(family, aidArr, qtyArr,
                        txtUrgencyType.getText(), (int) spnUrgencyLevel.getValue());
            } else {
                req = new AidRequest(family, aidArr, qtyArr);
            }

            requests.add(req);

            // Add to table
            StringBuilder aidStr = new StringBuilder();
            for (String t : aidArr) aidStr.append(t).append(" ");

            tableModel.addRow(new Object[]{
                    req.getRequestId(),
                    family.getName(),
                    family.getLocation(),
                    family.getMemberCount(),
                    aidStr.toString().trim(),
                    req.getPriorityScore(),
                    req.getStatus()
            });

            updateStats();
            clearForm();

        } catch (InvalidRequestException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "خطأ في الإدخال", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStats() {
        int pending = 0, approved = 0, delivered = 0;
        for (AidRequest r : requests) {
            switch (r.getStatus()) {
                case "قيد الانتظار": pending++; break;
                case "موافق عليه":   approved++; break;
                case "تم التسليم":   delivered++; break;
            }
        }
        lblStats.setText("إجمالي: " + requests.size()
                + " | قيد الانتظار: " + pending
                + " | موافق: " + approved
                + " | مسلّم: " + delivered);
    }

    private void clearForm() {
        txtFamilyName.setText(""); txtLocation.setText("");
        txtPhone.setText(""); txtMembers.setText("1");
        chkDisabled.setSelected(false); chkChronicIll.setSelected(false);
        chkUrgent.setSelected(false);
        for (JCheckBox cb : chkAidTypes) cb.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
